function setupOrderFilters() {
  const orderFilters = document.getElementById("orderFilters");
  if (!orderFilters) return;

  const select = orderFilters.querySelector("select[name='filtro']");
  const update = () => {
    const mode = select.value;
    orderFilters.querySelectorAll(".filter-date").forEach((el) => {
      el.style.display = mode === "date" ? "block" : "none";
      el.required = mode === "date";
    });
    orderFilters.querySelectorAll(".filter-user").forEach((el) => {
      el.style.display = mode === "utente" ? "block" : "none";
      el.required = mode === "utente";
    });
  };
  select.addEventListener("change", update);
  update();
}

function setupTableSearch() {
  document.querySelectorAll("[data-table-search]").forEach((input) => {
    const table = document.querySelector(input.dataset.tableSearch);
    if (!table) return;
    input.addEventListener("input", () => {
      const needle = input.value.trim().toLowerCase();
      table.querySelectorAll("tbody tr").forEach((row) => {
        row.hidden = needle !== "" && !row.textContent.toLowerCase().includes(needle);
      });
    });
  });
}

export function setupAdminInteractions() {
  setupOrderFilters();
  setupTableSearch();
}
