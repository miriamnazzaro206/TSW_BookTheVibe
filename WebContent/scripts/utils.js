(function () {
  window.BTV = window.BTV || {};

  window.BTV.fallbackImage = "https://images.unsplash.com/photo-1491557345352-5929e343eb89?auto=format&fit=crop&w=600&q=80";

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
