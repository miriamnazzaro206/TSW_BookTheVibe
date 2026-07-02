(function () {
  window.BTV = window.BTV || {};

  function requestJson(options, onSuccess, onError, onComplete) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
      var json;
      if (xhr.readyState !== 4) return;
      if (xhr.status >= 200 && xhr.status < 300) {
        try {
          json = JSON.parse(xhr.responseText);
          onSuccess(json);
        } catch (error) {
          if (onError) onError();
        }
      } else if (onError) {
        onError();
      }
      if (onComplete) onComplete();
    };
    xhr.open(options.method || "GET", options.url, true);
    xhr.setRequestHeader("Accept", "application/json");
    if (options.contentType) {
      xhr.setRequestHeader("Content-Type", options.contentType);
    }
    xhr.send(options.body || null);
  }

  function renderActivityCard(activity, contextPath) {
    var card = document.createElement("a");
    var img;
    card.className = "activity-card";
    card.href = contextPath + "/common/attivita?id=" + activity.id;
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
      requestJson({
        url: couponForm.getAttribute("data-url"),
        method: "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        body: formData.toString()
      }, function (json) {
        if (json.valid) {
          document.getElementById("cartTotal").textContent = json.totale;
          message.textContent = "Sconto applicato.";
          message.classList.add("visible", "is-success");
        } else {
          message.textContent = json.message || "Codice non valido.";
          message.classList.add("visible");
        }
      }, function () {
        message.textContent = "Non riesco ad applicare il codice in questo momento. Ricarica la pagina e riprova.";
        message.classList.add("visible");
      }, function () {
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
        requestJson({
          url: input.getAttribute("data-url"),
          method: "POST",
          contentType: "application/x-www-form-urlencoded;charset=UTF-8",
          body: formData.toString()
        }, function (json) {
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
        }, function () {
          if (message) {
            message.textContent = "Non riesco ad aggiornare la quantita.";
            message.classList.add("visible");
            message.classList.remove("is-success");
          }
        }, function () {
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
      params.set("categoria", category.value);
      params.set("citta", city.value);
      grid.classList.add("is-loading");
      count.textContent = "Aggiornamento risultati...";

      requestJson({ url: toolbar.getAttribute("data-url") + "?" + params.toString() }, function (activities) {
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
      }, function () {
        count.textContent = "Non riesco ad aggiornare il catalogo.";
      }, function () {
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

      requestJson({ url: panel.getAttribute("data-availability-url") + "?" + params.toString() }, function (json) {
        posti = Number(json.posti || posti);
        if (option) option.setAttribute("data-posti", posti);
        quantity.max = posti;
        if (Number(quantity.value) > posti) quantity.value = posti;
        message.textContent = posti === 1 ? "Rimane 1 posto disponibile." : "Rimangono " + posti + " posti disponibili.";
        if (validateQuantity) window.BTV.validateInput(quantity);
      }, function () {
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
