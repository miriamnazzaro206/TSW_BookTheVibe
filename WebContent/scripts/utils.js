export const fallbackImage = "https://images.unsplash.com/photo-1491557345352-5929e343eb89?auto=format&fit=crop&w=600&q=80";

export function debounce(fn, wait) {
  let timer;
  return (...args) => {
    window.clearTimeout(timer);
    timer = window.setTimeout(() => fn(...args), wait);
  };
}

export function addAccessTokens() {
  document.querySelectorAll("form[method='post'], form[method='POST']").forEach((form) => {
    if (!form.querySelector("input[name='accessToken']") && window.BTV_ACCESS_TOKEN) {
      const token = document.createElement("input");
      token.type = "hidden";
      token.name = "accessToken";
      token.value = window.BTV_ACCESS_TOKEN;
      form.appendChild(token);
    }
  });
}

export function euro(value) {
  return `\u20ac ${value}`;
}
