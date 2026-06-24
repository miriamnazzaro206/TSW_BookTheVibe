import { debounce, euro, fallbackImage } from "./utils.js";
import { validateInput } from "./validation.js";

function renderActivityCard(activity, contextPath) {
  const card = document.createElement("a");
  card.className = "activity-card";
  card.href = `${contextPath}/attivita?id=${activity.id}`;
  card.innerHTML = `
    <img src="${contextPath}/image?attivitaId=${activity.id}" alt="">
    <strong></strong>
    <span></span>
  `;
  card.querySelector("img").alt = activity.titolo;
  card.querySelector("img").onerror = function () { this.src = fallbackImage; };
  card.querySelector("strong").textContent = activity.titolo;
  card.querySelector("span").textContent = `${activity.citta} - ${activity.categoria} - ${euro(activity.prezzo)}`;
  return card;
}

function setupCouponAjax() {
  const couponForm = document.getElementById("couponForm");
  if (!couponForm) return;

  couponForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const button = couponForm.querySelector("button");
    const message = document.getElementById("couponMessage");
    button.disabled = true;
    button.textContent = "Verifico...";
    message.classList.remove("visible", "is-success");
    try {
      const formData = new FormData(couponForm);
      formData.append("action", "sconto");
      const res = await fetch(couponForm.dataset.url, { method: "POST", body: formData });
      const json = await res.json();
      if (json.valid) {
        document.getElementById("cartTotal").textContent = json.totale;
        message.textContent = "Sconto applicato.";
        message.classList.add("visible", "is-success");
      } else {
        message.textContent = json.message || "Codice non valido.";
        message.classList.add("visible");
      }
    } catch (error) {
      message.textContent = "Non riesco ad applicare il codice in questo momento.";
      message.classList.add("visible");
    } finally {
      button.disabled = false;
      button.textContent = "Applica";
    }
  });
}

function setupCartQuantityAjax() {
  document.querySelectorAll(".cart-quantity").forEach((input) => {
    const message = document.getElementById("cartMessage");
    const update = debounce(async () => {
      if (Number(input.value) < 1) {
        input.value = 1;
      }
      input.disabled = true;
      if (message) {
        message.textContent = "Aggiorno quantita...";
        message.classList.add("visible", "is-success");
      }
      try {
        const formData = new FormData();
        formData.append("action", "aggiorna");
        formData.append("ajax", "true");
        formData.append("id", input.dataset.id);
        formData.append("data", input.dataset.data);
        formData.append("quantita", input.value);
        if (window.BTV_ACCESS_TOKEN) formData.append("accessToken", window.BTV_ACCESS_TOKEN);
        const res = await fetch(input.dataset.url, { method: "POST", body: formData });
        const json = await res.json();
        if (json.valid) {
          const total = document.getElementById("cartTotal");
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
      } catch (error) {
        if (message) {
          message.textContent = "Non riesco ad aggiornare la quantita.";
          message.classList.add("visible");
          message.classList.remove("is-success");
        }
      } finally {
        input.disabled = false;
      }
    }, 350);
    input.addEventListener("change", update);
    input.addEventListener("input", update);
  });
}

function setupCatalogAjax() {
  const toolbar = document.getElementById("catalogToolbar");
  if (!toolbar) return;

  const grid = document.getElementById("catalogGrid");
  const search = document.getElementById("catalogSearch");
  const category = document.getElementById("catalogCategory");
  const city = document.getElementById("catalogCity");
  const count = document.getElementById("catalogCount");
  const contextPath = toolbar.dataset.context;

  async function loadCatalog() {
    const params = new URLSearchParams({
      q: search.value.trim(),
      categoria: category.value,
      citta: city.value
    });
    grid.classList.add("is-loading");
    count.textContent = "Aggiornamento risultati...";
    try {
      const res = await fetch(`${toolbar.dataset.url}?${params.toString()}`);
      const activities = await res.json();
      grid.innerHTML = "";
      if (activities.length === 0) {
        const empty = document.createElement("p");
        empty.className = "empty-state";
        empty.textContent = "Nessuna attivita trovata con questi filtri.";
        grid.appendChild(empty);
      } else {
        activities.forEach((activity) => grid.appendChild(renderActivityCard(activity, contextPath)));
      }
      count.textContent = `${activities.length} risultati`;
    } catch (error) {
      count.textContent = "Non riesco ad aggiornare il catalogo.";
    } finally {
      grid.classList.remove("is-loading");
    }
  }

  const debouncedLoad = debounce(loadCatalog, 280);
  search.addEventListener("input", debouncedLoad);
  category.addEventListener("change", loadCatalog);
  city.addEventListener("change", loadCatalog);
  count.textContent = `${grid.querySelectorAll(".activity-card").length} risultati`;
}

function setupAvailabilityAjax() {
  const panel = document.querySelector(".booking-panel[data-availability-url]");
  if (!panel) return;

  const select = document.getElementById("dataEvento");
  const quantity = document.getElementById("quantita");
  const message = document.getElementById("availabilityMessage");
  if (!select || !quantity || !message) return;

  async function updateAvailability() {
    const option = select.options[select.selectedIndex];
    let posti = Number(option ? option.dataset.posti : 0);
    message.textContent = "Controllo disponibilita...";
    try {
      const params = new URLSearchParams({ attivitaId: panel.dataset.attivitaId, data: select.value });
      const res = await fetch(`${panel.dataset.availabilityUrl}?${params.toString()}`);
      const json = await res.json();
      posti = Number(json.posti || posti);
      if (option) option.dataset.posti = posti;
    } catch (error) {
      message.textContent = "Disponibilita non aggiornata in tempo reale.";
      return;
    }
    quantity.max = posti;
    if (Number(quantity.value) > posti) quantity.value = posti;
    message.textContent = posti === 1 ? "Rimane 1 posto disponibile." : `Rimangono ${posti} posti disponibili.`;
    validateInput(quantity);
  }

  select.addEventListener("change", updateAvailability);
  quantity.addEventListener("input", () => validateInput(quantity));
  updateAvailability();
}

export function setupAjaxFeatures() {
  setupCouponAjax();
  setupCartQuantityAjax();
  setupCatalogAjax();
  setupAvailabilityAjax();
}
