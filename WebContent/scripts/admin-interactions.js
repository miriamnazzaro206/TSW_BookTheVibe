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

  function setupTableSearch() {
    Array.prototype.forEach.call(document.querySelectorAll("[data-table-search]"), function (input) {
      var table = document.querySelector(input.getAttribute("data-table-search"));
      if (!table) return;
      input.addEventListener("input", function () {
        var needle = input.value.trim().toLowerCase();
        Array.prototype.forEach.call(table.querySelectorAll("tbody tr"), function (row) {
          row.hidden = needle !== "" && row.textContent.toLowerCase().indexOf(needle) === -1;
        });
      });
    });
  }

  window.BTV.setupAdminInteractions = function () {
    setupOrderFilters();
    setupTableSearch();
  };
})();
