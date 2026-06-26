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

  window.BTV.addAccessTokens = function () {
    var forms = document.querySelectorAll("form[method='post'], form[method='POST']");
    Array.prototype.forEach.call(forms, function (form) {
      if (!form.querySelector("input[name='accessToken']") && window.BTV_ACCESS_TOKEN) {
        var token = document.createElement("input");
        token.type = "hidden";
        token.name = "accessToken";
        token.value = window.BTV_ACCESS_TOKEN;
        form.appendChild(token);
      }
    });
  };

  window.BTV.euro = function (value) {
    return "\u20ac " + value;
  };
})();
