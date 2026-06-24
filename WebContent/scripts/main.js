(async function () {
  const baseUrl = new URL(".", document.currentScript.src);
  const [{ addAccessTokens }, { setupValidation }, { setupAjaxFeatures }, { setupAdminInteractions }] = await Promise.all([
    import(new URL("utils.js", baseUrl)),
    import(new URL("validation.js", baseUrl)),
    import(new URL("ajax.js", baseUrl)),
    import(new URL("admin-interactions.js", baseUrl))
  ]);

  addAccessTokens();
  setupValidation();
  setupAjaxFeatures();
  setupAdminInteractions();
})();
