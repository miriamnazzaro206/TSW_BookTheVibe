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

  window.BTV.setupAdminInteractions = function () {
    setupOrderFilters();
  };
})();
