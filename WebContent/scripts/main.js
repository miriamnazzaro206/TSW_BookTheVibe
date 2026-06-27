(function () {
  var currentScript = document.currentScript;
  var baseUrl = currentScript ? currentScript.src.replace(/[^/]+$/, "") : "";
  var scripts = ["utils.js", "validation.js", "ajax.js", "admin-interactions.js"];

  function loadScript(index) {
    if (index >= scripts.length) {
      init();
      return;
    }
    var script = document.createElement("script");
    script.src = baseUrl + scripts[index];
    script.onload = function () {
      loadScript(index + 1);
    };
    document.head.appendChild(script);
  }

  function init() {
    if (window.BTV.setupValidation) window.BTV.setupValidation();
    if (window.BTV.setupAjaxFeatures) window.BTV.setupAjaxFeatures();
    if (window.BTV.setupAdminInteractions) window.BTV.setupAdminInteractions();
  }

  loadScript(0);
})();
