(function () {
  window.BTV = window.BTV || {};

  function renderActivityCard(activity, contextPath) {
    var card = document.createElement("a");
    var img;
    card.className = "activity-card";
    card.href = contextPath + "/attivita?id=" + activity.id;
    card.innerHTML = '<img src="' + contextPath + "/image?attivitaId=" + activity.id + '" alt=""><strong></strong><span></span>';
    img = card.querySelector("img");
    img.alt = activity.titolo;
    img.onerror = function () {
      this.src = window.BTV.fallbackImage;
    };
    card.querySelector("strong").textContent = activity.titolo;
    card.querySelector("span").textContent = activity.citta + " - " + activity.categoria + " - " + window.BTV.euro(activity.prezzo);
    return card;
  }

  function setupCouponAjax() {
    var couponForm = document.getElementById("couponForm");
    if (!couponForm) return;

    couponForm.addEventListener("submit", function (event) {
      var button;
      var message;
      var formData;
      event.preventDefault();
      button = couponForm.querySelector("button");
      message = document.getElementById("couponMessage");
      button.disabled = true;
      button.textContent = "Verifico...";
      message.classList.remove("visible", "is-success");

      formData = new URLSearchParams(new FormData(couponForm));
      formData.set("action", "sconto");
      if (!formData.has("accessToken") && window.BTV_ACCESS_TOKEN) {
        formData.set("accessToken", window.BTV_ACCESS_TOKEN);
      }

      fetch(couponForm.getAttribute("data-url"), {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8" },
        body: formData
      })
        .then(function (res) {
          if (!res.ok) throw new Error("Coupon request failed with " + res.status);
          return res.json();
        })
        .then(function (json) {
          if (json.valid) {
            document.getElementById("cartTotal").textContent = json.totale;
            message.textContent = "Sconto applicato.";
            message.classList.add("visible", "is-success");
          } else {
            message.textContent = json.message || "Codice non valido.";
            message.classList.add("visible");
          }
        })
        .catch(function () {
          message.textContent = "Non riesco ad applicare il codice in questo momento. Ricarica la pagina e riprova.";
          message.classList.add("visible");
        })
        .finally(function () {
          button.disabled = false;
          button.textContent = "Applica";
        });
    });
  }

  function setupCartQuantityAjax() {
    Array.prototype.forEach.call(document.querySelectorAll(".cart-quantity"), function (input) {
      var message = document.getElementById("cartMessage");
      var update = window.BTV.debounce(function () {
        var formData;
        if (Number(input.value) < 1) {
          input.value = 1;
        }
        input.disabled = true;
        if (message) {
          message.textContent = "Aggiorno quantita...";
          message.classList.add("visible", "is-success");
        }

        formData = new URLSearchParams();
        formData.set("action", "aggiorna");
        formData.set("ajax", "true");
        formData.set("id", input.getAttribute("data-id"));
        formData.set("data", input.getAttribute("data-data"));
        formData.set("quantita", input.value);
        if (window.BTV_ACCESS_TOKEN) formData.set("accessToken", window.BTV_ACCESS_TOKEN);

        fetch(input.getAttribute("data-url"), {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8" },
          body: formData
        })
          .then(function (res) {
            return res.json();
          })
          .then(function (json) {
            var total;
            if (json.valid) {
              total = document.getElementById("cartTotal");
              if (total) total.textContent = json.totale;
              if (message) {
                message.textContent = "";
                message.classList.remove("visible", "is-success");
              }
            } else if (message) {
              message.textContent = json.message || "Quantita non disponibile.";
              message.classList.add("visible");
              message.classList.remove("is-success");
            }
          })
          .catch(function () {
            if (message) {
              message.textContent = "Non riesco ad aggiornare la quantita.";
              message.classList.add("visible");
              message.classList.remove("is-success");
            }
          })
          .finally(function () {
            input.disabled = false;
          });
      }, 350);
      input.addEventListener("change", update);
    });
  }

  function setupCatalogAjax() {
    var toolbar = document.getElementById("catalogToolbar");
    var grid;
    var category;
    var city;
    var count;
    var contextPath;
    if (!toolbar) return;

    grid = document.getElementById("catalogGrid");
    category = document.getElementById("catalogCategory");
    city = document.getElementById("catalogCity");
    count = document.getElementById("catalogCount");
    contextPath = toolbar.getAttribute("data-context");

    function loadCatalog() {
      var params = new URLSearchParams();
      params.set("q", "");
      params.set("categoria", category.value);
      params.set("citta", city.value);
      grid.classList.add("is-loading");
      count.textContent = "Aggiornamento risultati...";

      fetch(toolbar.getAttribute("data-url") + "?" + params.toString())
        .then(function (res) {
          return res.json();
        })
        .then(function (activities) {
          grid.innerHTML = "";
          if (activities.length === 0) {
            var empty = document.createElement("p");
            empty.className = "empty-state";
            empty.textContent = "Nessuna attivita trovata con questi filtri.";
            grid.appendChild(empty);
          } else {
            activities.forEach(function (activity) {
              grid.appendChild(renderActivityCard(activity, contextPath));
            });
          }
          count.textContent = activities.length + " risultati";
        })
        .catch(function () {
          count.textContent = "Non riesco ad aggiornare il catalogo.";
        })
        .finally(function () {
          grid.classList.remove("is-loading");
        });
    }

    category.addEventListener("change", loadCatalog);
    city.addEventListener("change", loadCatalog);
    count.textContent = grid.querySelectorAll(".activity-card").length + " risultati";
  }

  function setupAvailabilityAjax() {
    var panel = document.querySelector(".booking-panel[data-availability-url]");
    var select;
    var quantity;
    var message;
    if (!panel) return;

    select = document.getElementById("dataEvento");
    quantity = document.getElementById("quantita");
    message = document.getElementById("availabilityMessage");
    if (!select || !quantity || !message) return;

    function updateAvailability(validateQuantity) {
      var option = select.options[select.selectedIndex];
      var posti = Number(option ? option.getAttribute("data-posti") : 0);
      var params = new URLSearchParams();
      message.textContent = "Controllo disponibilita...";
      params.set("attivitaId", panel.getAttribute("data-attivita-id"));
      params.set("data", select.value);

      fetch(panel.getAttribute("data-availability-url") + "?" + params.toString())
        .then(function (res) {
          return res.json();
        })
        .then(function (json) {
          posti = Number(json.posti || posti);
          if (option) option.setAttribute("data-posti", posti);
          quantity.max = posti;
          if (Number(quantity.value) > posti) quantity.value = posti;
          message.textContent = posti === 1 ? "Rimane 1 posto disponibile." : "Rimangono " + posti + " posti disponibili.";
          if (validateQuantity) window.BTV.validateInput(quantity);
        })
        .catch(function () {
          message.textContent = "Disponibilita non aggiornata in tempo reale.";
        });
    }

    select.addEventListener("change", function () {
      updateAvailability(true);
    });
    quantity.addEventListener("change", function () {
      window.BTV.validateInput(quantity);
    });
    updateAvailability(false);
  }

  window.BTV.setupAjaxFeatures = function () {
    setupCouponAjax();
    setupCartQuantityAjax();
    setupCatalogAjax();
    setupAvailabilityAjax();
  };
})();
