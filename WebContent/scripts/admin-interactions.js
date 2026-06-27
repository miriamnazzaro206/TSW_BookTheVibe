(function () {
  window.BTV = window.BTV || {};

  function setupOrderFilters() {
    var orderFilters = document.getElementById("orderFilters");
    if (!orderFilters) return;

    var select = orderFilters.querySelector("select[name='filtro']");
    var update = function () {
      var mode = select.value;
      Array.prototype.forEach.call(orderFilters.querySelectorAll(".filter-date"), function (el) {
        el.style.display = mode === "date" ? "block" : "none";
        el.required = mode === "date";
      });
      Array.prototype.forEach.call(orderFilters.querySelectorAll(".filter-user"), function (el) {
        el.style.display = mode === "utente" ? "block" : "none";
        el.required = mode === "utente";
      });
    };
    select.addEventListener("change", update);
    update();
  }

  function setupDateLists() {
    Array.prototype.forEach.call(document.querySelectorAll("[data-date-list]"), function (control) {
      var hidden = control.querySelector("input[type='hidden'][name='date_evento']");
      var picker = control.querySelector(".date-picker-input");
      var addButton = control.querySelector(".date-add-button");
      var chips = control.querySelector(".date-chip-list");
      var form = control.closest("form");

      if (!hidden || !picker || !addButton || !chips) return;

      function getDates() {
        return hidden.value ? hidden.value.split(",").filter(Boolean) : [];
      }

      function setDates(values) {
        hidden.value = values.join(",");
      }

      function setError(message) {
        var error = control.querySelector(".date-list-error") || control.nextElementSibling;
        if (error && error.classList.contains("error")) {
          error.textContent = message || "";
          error.classList.toggle("visible", Boolean(message));
        }
      }

      function renderDates() {
        var values = getDates();
        chips.innerHTML = "";
        values.forEach(function (value) {
          var chip = document.createElement("button");
          chip.type = "button";
          chip.className = "date-chip";
          chip.textContent = value + " x";
          chip.setAttribute("aria-label", "Rimuovi data " + value);
          chip.addEventListener("click", function () {
            setDates(getDates().filter(function (date) {
              return date !== value;
            }));
            renderDates();
          });
          chips.appendChild(chip);
        });
      }

      addButton.addEventListener("click", function () {
        var value = picker.value;
        var values = getDates();
        if (!value) {
          picker.focus();
          return;
        }
        if (values.indexOf(value) === -1) {
          values.push(value);
          values.sort();
          setDates(values);
          renderDates();
        }
        picker.value = "";
        setError("");
      });

      if (form) {
        form.addEventListener("submit", function (event) {
          if (control.hasAttribute("data-date-required") && !hidden.value) {
            event.preventDefault();
            setError("Aggiungi almeno una data.");
            picker.focus();
          }
        });
      }

      renderDates();
    });
  }

  window.BTV.setupAdminInteractions = function () {
    setupOrderFilters();
    setupDateLists();
  };
})();
