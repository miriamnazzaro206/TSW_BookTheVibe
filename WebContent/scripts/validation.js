(function () {
  window.BTV = window.BTV || {};

  var patterns = {
    email: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
    password: /^.{6,}$/,
    phone: /^[0-9 +()-]{6,20}$/,
    cap: /^[0-9]{5}$/,
    card: /^[0-9]{13,19}$/,
    expiry: /^(0[1-9]|1[0-2])\/[0-9]{2}$/,
    cvv: /^[0-9]{3,4}$/
  };

  function messageFor(input) {
    if (input.validity.valueMissing) return "Campo obbligatorio.";
    var key = input.getAttribute("data-pattern");
    if (key === "email") return "Inserisci una email valida.";
    if (key === "password") return "La password deve avere almeno 6 caratteri.";
    if (key === "phone") return "Inserisci un numero di telefono valido.";
    if (key === "cap") return "Il CAP deve contenere 5 cifre.";
    if (key === "card") return "Inserisci solo le cifre della carta.";
    if (key === "expiry") return "Usa il formato MM/AA.";
    if (key === "cvv") return "Il CVV deve contenere 3 o 4 cifre.";
    if (input.id === "quantita") return "Seleziona una quantita tra 1 e " + (input.max || 1) + ".";
    return "Valore non valido.";
  }

  function errorNode(input) {
    var node = input.nextElementSibling;
    if (!node || !node.classList.contains("error")) {
      node = input.parentElement && input.parentElement.querySelector(".error");
    }
    return node;
  }

  window.BTV.validateInput = function (input) {
    var valid = input.checkValidity();
    var key = input.getAttribute("data-pattern");
    if (valid && key && patterns[key]) valid = patterns[key].test(input.value.trim());

    if (input.id === "quantita") {
      var selected = document.querySelector("#dataEvento option:checked");
      var max = selected ? Number(selected.getAttribute("data-posti") || input.max || 1) : Number(input.max || 1);
      input.max = max;
      valid = valid && Number(input.value) >= 1 && Number(input.value) <= max;
    }

    var node = errorNode(input);
    if (node) {
      node.textContent = valid ? "" : messageFor(input);
      node.classList.toggle("visible", !valid);
    }
    input.classList.toggle("field-error", !valid);
    input.classList.toggle("field-valid", valid && input.value.trim() !== "");
    return valid;
  };

  window.BTV.setupValidation = function () {
    var fields = document.querySelectorAll(".validated-form input, .validated-form select, .validated-form textarea");
    Array.prototype.forEach.call(fields, function (input) {
      input.addEventListener("change", function () {
        window.BTV.validateInput(input);
      });
    });

    var forms = document.querySelectorAll(".validated-form");
    Array.prototype.forEach.call(forms, function (form) {
      form.addEventListener("submit", function (event) {
        var formFields = Array.prototype.filter.call(form.querySelectorAll("input, select, textarea"), function (field) {
          return field.type !== "hidden" && field.type !== "file";
        });
        var valid = formFields.every(window.BTV.validateInput);
        if (!valid) {
          event.preventDefault();
          var firstError = form.querySelector(".field-error");
          if (firstError) firstError.focus();
        }
      });
    });
  };
})();
