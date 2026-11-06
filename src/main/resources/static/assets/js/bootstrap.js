const views = [
  ["/assets/views/header.html", "#view-header"],
  ["/assets/views/home.html", "#app-content"],
  ["/assets/views/auth.html", "#app-content"],
  ["/assets/views/farmer.html", "#app-content"],
  ["/assets/views/catalog.html", "#app-content"],
  ["/assets/views/cart.html", "#app-content"],
  ["/assets/views/account.html", "#app-content"],
  ["/assets/views/admin.html", "#app-content"],
  ["/assets/views/dialog.html", "#view-dialog"],
  ["/assets/views/footer.html", "#view-footer"]
];

try {
  const fragments = await Promise.all(views.map(async ([path]) => {
    const response = await fetch(path);
    if (!response.ok) throw new Error(`No se pudo cargar ${path} (${response.status})`);
    return response.text();
  }));

  views.forEach(([, selector], index) => {
    document.querySelector(selector).insertAdjacentHTML("beforeend", fragments[index]);
  });
  document.querySelector("#app-content").setAttribute("aria-busy", "false");
  await import("/assets/js/app.js");
} catch (error) {
  const app = document.querySelector("#app-content");
  app.setAttribute("aria-busy", "false");
  app.innerHTML = '<section class="load-error" role="alert"><h1>No pudimos cargar AgroValle</h1><p>Revisa tu conexión e inténtalo de nuevo.</p></section>';
  console.error("Error al iniciar AgroValle:", error);
}
