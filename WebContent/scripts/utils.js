(function () {
  var currentScript = document.currentScript;
  var assetBaseUrl = currentScript ? currentScript.src.replace(/scripts\/[^/]+$/, "") : "";

  window.BTV = window.BTV || {};

  window.BTV.fallbackImage = assetBaseUrl + "images/logo.png";

  window.BTV.debounce = function (fn, wait) {
    var timer;
    return function () {
      var args = arguments;
      window.clearTimeout(timer);
      timer = window.setTimeout(function () {
        fn.apply(null, args);
      }, wait);
    };
  };

  window.BTV.euro = function (value) {
    return "\u20ac " + value;
  };
})();
