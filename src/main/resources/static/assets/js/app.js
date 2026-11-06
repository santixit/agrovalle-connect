const api = '/api/v1';
    let token = null;
    let rol = null;
    let modoAuth = 'login';
    let vistaActividad = null;
    let carrito;
    try {
      carrito = new Map(JSON.parse(sessionStorage.getItem('agrovalle-carrito') || '[]')
        .map(([id, item]) => [Number(id), item]));
    } catch { carrito = new Map(); }
    const byId = (id) => document.getElementById(id);
    const formData = (form) => Object.fromEntries(new FormData(form).entries());
    const tell = (id, text) => {
      const message = byId(id);
      message.textContent = text;
      if (id === 'auth-status') {
        message.classList.toggle('is-session-confirmation', Boolean(token) && text.startsWith('Sesión iniciada como '));
      }
    };
    const todayInBogota = () => {
      const parts = new Intl.DateTimeFormat('en', {
        timeZone: 'America/Bogota', year: 'numeric', month: '2-digit', day: '2-digit'
      }).formatToParts(new Date());
      const date = Object.fromEntries(parts.map(({ type, value }) => [type, value]));
      return `${date.year}-${date.month}-${date.day}`;
    };
    const harvestDate = byId('producto-form').elements.namedItem('fecha_cosecha');
    const setHarvestDateMinimum = () => {
      harvestDate.min = todayInBogota();
      if (!harvestDate.value || harvestDate.value < harvestDate.min) harvestDate.value = harvestDate.min;
    };
    setHarvestDateMinimum();
    const draftKey = 'agrovalle-oferta-borrador';
    let fincasRegistradas = [];
    let mostrarTodasFincas = false;
    const formatCop = (value) => new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0
    }).format(value);
    async function request(path, options = {}) {
      const headers = { ...(options.body ? { 'Content-Type': 'application/json' } : {}),
        ...(token ? { Authorization: `Bearer ${token}` } : {}), ...options.headers };
      const response = await fetch(api + path, { ...options, headers });
      if (response.status === 204) return null;
      const body = await response.json().catch(() => ({}));
      if (!response.ok) throw new Error(body.error || `Solicitud fallida (${response.status})`);
      return body;
    }
    function actualizarPaneles() {
      const vistaEnfocada = Boolean(token && vistaActividad);
      byId('acceso-seccion').classList.toggle('is-register', modoAuth === 'registro');
      byId('session-bar').hidden = !token;
      byId('session-identity').textContent = token ? `Sesión iniciada como ${rol}` : '';
      byId('acceso-seccion').hidden = Boolean(token);
      byId('header-login').href = token
        ? rol === 'AGRICULTOR' ? '#agricultor-panel' : rol === 'ADMIN' ? '#admin-panel' : '#cuenta-panel'
        : '#acceso-seccion';
      byId('acceso-titulo').textContent = token ? 'Tu cuenta' : modoAuth === 'registro' ? 'Crear cuenta' : 'Iniciar sesión';
      byId('auth-card-intro').textContent = modoAuth === 'registro'
        ? 'Cuéntanos quién eres para encontrar tu lugar en la red.'
        : 'Tu próxima conexión con el campo empieza aquí.';
      byId('login-form').hidden = Boolean(token) || modoAuth !== 'login';
      byId('registro-form').hidden = Boolean(token) || modoAuth !== 'registro';
      byId('auth-toggle').hidden = Boolean(token);
      byId('auth-toggle').textContent = modoAuth === 'registro'
        ? '¿Ya tienes cuenta? Iniciar sesión' : '¿No tienes cuenta? Crear cuenta';
      byId('salir').hidden = !token;
      byId('cuenta-panel').hidden = !token || vistaEnfocada;
      byId('actividad-vista').hidden = !vistaEnfocada;
      byId('inicio-seccion').hidden = vistaEnfocada;
      byId('como-funciona').hidden = vistaEnfocada;
      byId('app-content').classList.toggle('is-focused-route', vistaEnfocada);
      byId('catalogo-seccion').hidden = !token || vistaEnfocada;
      byId('catalog-search-form').hidden = !token;
      byId('catalog-nav').hidden = !token;
      byId('prices-nav').hidden = !token;
      byId('agricultor-panel').hidden = rol !== 'AGRICULTOR' || vistaEnfocada;
      byId('admin-panel').hidden = rol !== 'ADMIN' || vistaEnfocada;
      byId('carrito-seccion').hidden = rol !== 'COMPRADOR' || vistaEnfocada;
      byId('carrito-link').hidden = rol !== 'COMPRADOR';
      renderCarrito();
      if (rol === 'AGRICULTOR') { cargarFincas(); cargarPanelAgricultor(); }
      if (rol === 'ADMIN') cargarReporteAdmin();
    }
    function cambiarModoAuth(modo) {
      if (token) return;
      modoAuth = modo;
      byId('acceso-seccion').classList.toggle('is-register', modo === 'registro');
      actualizarPaneles();
      tell('auth-status', '');
    }
    byId('auth-toggle').addEventListener('click', () => {
      cambiarModoAuth(modoAuth === 'login' ? 'registro' : 'login');
    });
    async function cargarPanelAgricultor() {
      try {
        const [messages, products, orders] = await Promise.all([
          request('/notificaciones/mias'), request('/productos/mias'), request('/reservas/pendientes')
        ]);
        const contacts = messages.filter((item) => item.tipo === 'CONTACTO' && !item.leida).length;
        const active = orders.length;
        const pending = orders.filter((item) => item.estado === 'PENDIENTE' || item.estado === 'CONFIRMADO').length;
        const inCourse = orders.filter((item) => item.estado === 'EN_DESPACHO').length;
        byId('farmer-contact-count').textContent = String(contacts);
        byId('farmer-contact-count').classList.remove('is-placeholder');
        byId('farmer-product-count').textContent = String(products.length);
        byId('farmer-product-count').classList.remove('is-placeholder');
        byId('farmer-order-count').textContent = String(active);
        byId('farmer-order-count').classList.remove('is-placeholder');
        byId('farmer-delivery-count').textContent = String(inCourse);
        byId('farmer-delivery-count').classList.remove('is-placeholder');
        byId('farmer-pending-count').textContent = String(pending);
        byId('farmer-pending-count').classList.remove('is-placeholder');
        byId('farmer-course-count').textContent = String(inCourse);
        byId('farmer-course-count').classList.remove('is-placeholder');
        byId('farmer-delivered-count').textContent = '—';
      } catch (error) { tell('farmer-dashboard-status', error.message); }
    }
    byId('login-form').addEventListener('submit', async (event) => {
      event.preventDefault();
      const form = event.currentTarget;
      try {
        const result = await request('/auth/login', { method: 'POST', body: JSON.stringify(formData(form)) });
        token = result.accessToken;
        rol = JSON.parse(atob(token.split('.')[1])).scope;
        form.reset();
        actualizarPaneles();
        await buscar();
        tell('auth-status', `Sesión iniciada como ${rol}.`);
      } catch (error) { tell('auth-status', error.message); }
    });
    byId('salir').addEventListener('click', () => { token = null; rol = null; modoAuth = 'login'; vistaActividad = null; carrito.clear(); actualizarPaneles(); tell('auth-status', 'Sesión cerrada.'); });
    const registroForm = byId('registro-form');
    function actualizarCamposRegistro(tipo) {
      document.querySelectorAll('[data-farmer]').forEach((field) => {
        field.hidden = tipo !== 'AGRICULTOR';
        field.querySelector('input').required = !field.hidden;
      });
      document.querySelectorAll('[data-buyer]').forEach((field) => {
        field.hidden = tipo !== 'COMPRADOR';
      });
      registroForm.elements.namedItem('ubicacion_valle').required = tipo === 'AGRICULTOR';
    }
    actualizarCamposRegistro(registroForm.elements.namedItem('tipo').value);
    registroForm.addEventListener('change', (event) => {
      if (event.target.name === 'tipo') actualizarCamposRegistro(event.target.value);
    });
    byId('registro-form').addEventListener('submit', async (event) => {
      event.preventDefault();
      const form = event.currentTarget;
      const data = formData(form);
      const type = data.tipo;
      delete data.tipo;
      const path = type === 'AGRICULTOR' ? '/auth/register' : '/auth/register/comprador';
      try {
        await request(path, { method: 'POST', body: JSON.stringify(data) });
        form.reset();
        actualizarCamposRegistro(form.elements.namedItem('tipo').value);
        cambiarModoAuth('login');
        tell('auth-status', 'Cuenta creada. Ya puedes iniciar sesión.');
      } catch (error) { tell('auth-status', error.message); }
    });
    async function cargarFincas() {
      try {
        const fincas = await request('/fincas');
        fincasRegistradas = fincas;
        const farmSelect = byId('parcela-finca-select');
        farmSelect.replaceChildren(new Option('Selecciona una finca', ''));
        fincas.forEach((finca) => farmSelect.add(new Option(`${finca.nombre} · ${finca.municipio}`, finca.id)));
        const parcelas = await request('/parcelas/mias');
        const select = byId('parcela-select');
        select.replaceChildren(new Option('Sin finca asociada', ''));
        parcelas.forEach((parcela) => {
          const option = new Option(`${parcela.finca} · ${parcela.codigo} · ${parcela.municipio}`, parcela.id);
          option.dataset.municipio = parcela.municipio;
          select.add(option);
        });
        const draft = JSON.parse(localStorage.getItem(draftKey) || 'null');
        if (draft?.parcelaId) select.value = draft.parcelaId;
        const selectedMunicipality = select.selectedOptions[0]?.dataset.municipio;
        const municipalityField = byId('producto-form').elements.namedItem('municipio');
        if (selectedMunicipality) municipalityField.value = selectedMunicipality;
        municipalityField.readOnly = Boolean(selectedMunicipality);
        renderFincas();
      } catch (error) { tell('farm-status', error.message); }
    }
    function renderFincas() {
        const fincas = mostrarTodasFincas ? fincasRegistradas : fincasRegistradas.slice(0, 2);
        const farmItems = fincas.map((finca) => {
          const item = document.createElement('article'); item.className = 'farm-item';
          const name = document.createElement('strong'); name.textContent = finca.nombre;
          const place = document.createElement('span'); place.textContent = `${finca.municipio} · Agricultor`;
          item.append(name, place); return item;
        });
        byId('fincas-list').replaceChildren(...(farmItems.length ? farmItems
          : [Object.assign(document.createElement('p'), { className: 'muted', textContent: token ? 'Todavía no tienes fincas registradas.' : 'Inicia sesión para ver tus fincas.' })]));
        const more = byId('ver-mas-fincas');
        more.hidden = fincasRegistradas.length <= 2;
        more.textContent = mostrarTodasFincas ? 'Ver menos' : 'Ver más';
    }
    byId('ver-mas-fincas').addEventListener('click', () => { mostrarTodasFincas = !mostrarTodasFincas; renderFincas(); });
    byId('parcela-select').addEventListener('change', (event) => {
      const municipality = event.currentTarget.selectedOptions[0]?.dataset.municipio;
      const field = byId('producto-form').elements.namedItem('municipio');
      field.readOnly = Boolean(municipality);
      if (municipality) field.value = municipality;
      else field.value = '';
    });
    byId('finca-form').addEventListener('submit', async (event) => {
      event.preventDefault();
      const form = event.currentTarget;
      try { await request('/fincas', { method: 'POST', body: JSON.stringify(formData(form)) });
        tell('farm-status', 'Finca registrada correctamente.'); form.reset(); await cargarFincas();
      } catch (error) { tell('farm-status', error.message); }
    });
    byId('parcela-form').addEventListener('submit', async (event) => {
      event.preventDefault();
      const form = event.currentTarget;
      const data = formData(form);
      const fincaId = Number(data.fincaId);
      delete data.fincaId;
      try {
        await request(`/fincas/${fincaId}/parcelas`, { method: 'POST', body: JSON.stringify(data) });
        tell('farm-status', 'Parcela registrada correctamente.'); form.reset(); await cargarFincas();
      } catch (error) { tell('farm-status', error.message); }
    });
    byId('producto-form').addEventListener('submit', async (event) => {
      event.preventDefault();
      const form = event.currentTarget;
      const data = formData(form);
      data.cantidadKg = Number(data.cantidadKg); data.precioPorKg = Number(data.precioPorKg);
      data.parcelaId = data.parcelaId ? Number(data.parcelaId) : null;
      try { await request('/productos', { method: 'POST', body: JSON.stringify(data) });
        localStorage.removeItem(draftKey);
        tell('product-status', 'Oferta publicada correctamente.'); form.reset(); setHarvestDateMinimum();
        byId('producto-imagen').value = ''; byId('imagen-preview').textContent = 'Imagen previa';
        await buscar();
      } catch (error) { tell('product-status', error.message); }
    });
    byId('guardar-borrador').addEventListener('click', () => {
      localStorage.setItem(draftKey, JSON.stringify(formData(byId('producto-form'))));
      tell('product-status', 'Borrador guardado en este navegador.');
    });
    try {
      const draft = JSON.parse(localStorage.getItem(draftKey) || 'null');
      if (draft) Object.entries(draft).forEach(([name, value]) => {
        const field = byId('producto-form').elements.namedItem(name);
        if (field) field.value = value;
      });
    } catch { localStorage.removeItem(draftKey); }
    byId('producto-imagen').addEventListener('change', (event) => {
      const file = event.target.files?.[0];
      if (!file) return;
      if (!file.type.startsWith('image/')) { tell('product-status', 'Selecciona un archivo de imagen.'); return; }
      const preview = document.createElement('img');
      preview.alt = 'Vista previa de la foto de la cosecha';
      preview.src = URL.createObjectURL(file);
      byId('imagen-preview').replaceChildren(preview);
    });
    function mostrarPrecioChart(container, average, comparison, labels) {
      const chart = document.createElement('div'); chart.className = 'price-chart';
      const title = document.createElement('p'); title.className = 'price-chart-title';
      title.textContent = 'Precio regional de referencia';
      const track = document.createElement('div'); track.className = 'price-chart-track';
      const max = Math.max(Number(average) || 0, Number(comparison) || 0, 1);
      const values = comparison == null ? [average] : [average, comparison];
      values.forEach((value, index) => {
        const bar = document.createElement('span'); bar.className = `price-chart-bar${index ? ' secondary' : ''}`;
        bar.style.height = `${Math.max(8, Math.round((Number(value) / max) * 100))}%`;
        bar.title = `${labels[index]}: ${formatCop(value)}/kg`; bar.setAttribute('aria-label', bar.title);
        track.append(bar);
      });
      const captions = document.createElement('div'); captions.className = 'price-chart-labels';
      labels.slice(0, values.length).forEach((label) => { const item = document.createElement('span'); item.textContent = label; captions.append(item); });
      chart.append(title, track, captions); container.replaceChildren(chart);
    }
    async function cargarDetalle(producto) {
      try {
        const detalle = await request(`/productos/${producto.id}`);
        const dialog = byId('producto-dialog'); const content = byId('detalle-contenido');
        const layout = document.createElement('div'); layout.className = 'detail-layout';
        const left = document.createElement('div');
        const photo = document.createElement('div'); photo.className = 'detail-photo'; photo.textContent = 'Foto del producto';
        const info = document.createElement('section'); info.className = 'detail-info';
        const title = document.createElement('h2'); title.id = 'detalle-titulo'; title.textContent = detalle.nombre;
        const origin = document.createElement('p'); origin.className = 'muted';
        origin.textContent = `${detalle.finca || 'Finca no informada'} · ${detalle.municipio} · ${detalle.agricultor || 'Agricultor no informado'}`;
        const facts = document.createElement('p'); facts.className = 'detail-facts';
        facts.textContent = `${formatCop(detalle.precio_por_kg)}/kg · ${detalle.cantidad_disponible_kg} kg disponibles · Fecha disponible: ${detalle.fecha_cosecha}`;
        left.append(photo);
        info.append(title, origin, facts);
        const chart = document.createElement('div');
        try {
          const price = await request(`/precios/regionales?categoria=${encodeURIComponent(detalle.categoria)}`);
          if (price.transaccionesAnalizadas) mostrarPrecioChart(chart, price.promedioPorKg,
            detalle.precio_por_kg, ['Promedio regional', 'Esta oferta']);
          else chart.textContent = 'Aún no hay transacciones completadas para comparar precios en esta categoría.';
        } catch { chart.textContent = 'No se pudo consultar el precio regional en este momento.'; }
        const actions = document.createElement('div'); actions.className = 'detail-actions';
        if (token && rol === 'COMPRADOR') {
          const reserve = document.createElement('button'); reserve.textContent = 'Reservar';
          reserve.onclick = () => mostrarFormularioAccion(info, detalle, 'reserve');
          const contact = document.createElement('button'); contact.className = 'secondary'; contact.textContent = 'Contactar';
          contact.onclick = () => mostrarFormularioAccion(info, detalle, 'contact');
          const favorite = document.createElement('button'); favorite.className = 'secondary'; favorite.textContent = '☆ Guardar en favoritos';
          favorite.onclick = async () => { try { await request(`/favoritos/${detalle.id}`, { method: 'POST' }); tell('catalog-status', 'Oferta guardada en favoritos.'); favorite.textContent = '★ Guardado en favoritos'; } catch (error) { tell('catalog-status', error.message); } };
          actions.append(favorite, reserve, contact);
        } else {
          const login = document.createElement('a'); login.className = 'button-link secondary'; login.href = '#acceso-seccion';
          login.textContent = 'Ingresa para reservar o contactar'; actions.append(login);
        }
        info.append(chart, actions); layout.append(left, info); content.replaceChildren(layout);
        if (!dialog.open) dialog.showModal();
      } catch (error) { tell('catalog-status', error.message); }
    }
    byId('cerrar-detalle').addEventListener('click', () => byId('producto-dialog').close());
    byId('producto-dialog').addEventListener('click', (event) => {
      if (event.target === byId('producto-dialog')) byId('producto-dialog').close();
    });
    function tarjeta(producto) {
      const article = document.createElement('article'); article.className = 'card';
      const title = document.createElement('h3'); title.textContent = producto.nombre;
      const info = document.createElement('p'); info.textContent = `${producto.categoria} · ${producto.municipio}`;
      const amount = document.createElement('p');
      amount.textContent = producto.cantidad_kg == null ? 'Disponibilidad por confirmar'
        : `${producto.cantidad_disponible_kg ?? producto.cantidad_kg} kg disponibles · ${formatCop(producto.precio_por_kg)}/kg`;
      article.append(title, info, amount);
      const actions = document.createElement('div'); actions.className = 'actions';
      const detail = document.createElement('button'); detail.className = 'secondary'; detail.textContent = 'Ver detalles';
      detail.onclick = () => cargarDetalle(producto);
      actions.append(detail);
      const favorite = document.createElement('button'); favorite.className = 'secondary';
      favorite.textContent = '☆ Fav'; favorite.setAttribute('aria-label', `Guardar ${producto.nombre} en favoritos`);
      favorite.onclick = async () => {
        if (!token || rol !== 'COMPRADOR') {
          tell('catalog-status', 'Ingresa como comprador para guardar favoritos.');
          byId('acceso-seccion').scrollIntoView({ behavior: 'smooth', block: 'start' }); return;
        }
        try { await request(`/favoritos/${producto.id}`, { method: 'POST' }); favorite.textContent = '★ Fav'; }
        catch (error) { tell('catalog-status', error.message); }
      };
      actions.append(favorite);
      if (token && rol === 'COMPRADOR') {
        const reserve = document.createElement('button'); reserve.textContent = 'Reservar';
        reserve.onclick = () => mostrarFormularioAccion(article, producto, 'reserve');
        const add = document.createElement('button'); add.className = 'secondary';
        add.textContent = 'Agregar al carrito';
        add.onclick = () => mostrarFormularioAccion(article, producto, 'cart');
        const contact = document.createElement('button'); contact.className = 'secondary'; contact.textContent = 'Contactar';
        contact.onclick = () => mostrarFormularioAccion(article, producto, 'contact');
          actions.append(reserve, add, contact);
      }
      article.append(actions); return article;
    }
    function mostrarFormularioAccion(article, producto, tipo) {
      article.querySelector('.product-action-form')?.remove();
      const form = document.createElement('form'); form.className = 'product-action-form';
      const feedback = document.createElement('p'); feedback.className = 'status'; feedback.setAttribute('role', 'status');
      if (tipo === 'contact') {
        const label = document.createElement('label'); label.textContent = 'Mensaje para el agricultor';
        const message = document.createElement('textarea'); message.name = 'mensaje'; message.maxLength = 1000;
        message.required = true; message.placeholder = 'Cuéntale qué información necesitas…';
        label.append(message);
        const submit = document.createElement('button'); submit.textContent = 'Enviar mensaje';
        form.append(label, submit);
      } else {
        const label = document.createElement('label');
        label.textContent = tipo === 'reserve' ? 'Cantidad a reservar (kg)' : 'Cantidad para agregar (kg)';
        const quantity = document.createElement('input'); quantity.name = 'cantidad'; quantity.type = 'number';
        quantity.min = '0.01'; quantity.step = '0.01'; quantity.required = true;
        if (producto.cantidad_disponible_kg != null) {
          const enCarrito = tipo === 'cart' ? carrito.get(producto.id)?.cantidadKg ?? 0 : 0;
          quantity.max = Math.max(0, producto.cantidad_disponible_kg - enCarrito);
        }
        label.append(quantity);
        const submit = document.createElement('button'); submit.textContent = tipo === 'reserve'
          ? 'Confirmar reserva' : 'Agregar';
        form.append(label, submit);
      }
      form.append(feedback);
      form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const submit = form.querySelector('button'); submit.disabled = true;
        try {
          if (tipo === 'contact') {
            await request('/contactos', { method: 'POST', body: JSON.stringify({
              productoId: producto.id, mensaje: form.elements.namedItem('mensaje').value.trim()
            }) });
            feedback.textContent = 'Mensaje enviado. El agricultor recibirá una notificación en su cuenta.';
            form.reset();
          } else {
            const quantity = Number(form.elements.namedItem('cantidad').value);
            if (tipo === 'reserve') {
              await request('/reservas', { method: 'POST', body: JSON.stringify({
                productoId: producto.id, cantidad_kg: quantity
              }) });
              feedback.textContent = 'Reserva creada. Ya aparece en “Mi actividad” y el agricultor debe confirmarla.';
              form.reset();
              await buscar();
              await cargarPedidos();
              tell('catalog-status', 'Reserva creada. El agricultor debe confirmarla.');
              byId('cuenta-panel').scrollIntoView({ behavior: 'smooth', block: 'start' });
            } else {
              const current = carrito.get(producto.id);
              carrito.set(producto.id, { productoId: producto.id, nombre: producto.nombre,
                cantidadKg: (current?.cantidadKg ?? 0) + quantity });
              renderCarrito();
              tell('carrito-status', `${producto.nombre} se agregó al carrito.`);
              byId('carrito-seccion').scrollIntoView({ behavior: 'smooth', block: 'start' });
              form.reset();
            }
          }
        } catch (error) {
          feedback.textContent = error.message;
        } finally {
          submit.disabled = false;
        }
      });
      article.append(form);
      form.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }
    async function buscar() {
      const { municipio, categoria } = formData(byId('buscar-form'));
      const values = { municipio, categoria };
      const params = new URLSearchParams(Object.entries(values).filter(([, value]) => value.trim()));
      try {
        const query = byId('catalog-query').value.trim().toLocaleLowerCase('es');
        const allProducts = await request(`/productos${params.size ? `?${params}` : ''}`);
        const producer = (byId('buscar-form').elements.namedItem('productor')?.value || '').trim().toLocaleLowerCase('es');
        const products = allProducts.filter((product) => {
          const matchesQuery = !query || `${product.nombre} ${product.municipio} ${product.categoria} ${product.agricultor || ''}`.toLocaleLowerCase('es').includes(query);
          const matchesProducer = !producer || (product.agricultor || '').toLocaleLowerCase('es').includes(producer);
          return matchesQuery && matchesProducer;
        });
        if (products.length === 0) {
          const empty = document.createElement('p'); empty.className = 'empty-state';
          empty.textContent = 'No encontramos cosechas con esos filtros. Prueba con otro municipio o categoría.';
          byId('catalogo').replaceChildren(empty);
        } else {
          byId('catalogo').replaceChildren(...products.map(tarjeta));
        }
        tell('catalog-status', `${products.length} oferta(s) encontrada(s).`);
      } catch (error) { tell('catalog-status', error.message); }
    }
    function renderCarrito() {
      const lineas = [...carrito.values()].map((item) => {
        const card = document.createElement('article'); card.className = 'card';
        const detail = document.createElement('p');
        detail.textContent = `${item.nombre} · ${item.cantidadKg} kg`;
        const remove = document.createElement('button'); remove.className = 'secondary';
        remove.textContent = 'Quitar';
        remove.onclick = () => { carrito.delete(item.productoId); renderCarrito(); };
        card.append(detail, remove); return card;
      });
      byId('carrito-lineas').replaceChildren(...lineas);
      sessionStorage.setItem('agrovalle-carrito', JSON.stringify([...carrito.entries()]));
      byId('carrito-vacio').hidden = carrito.size > 0;
      byId('carrito-count').textContent = String(carrito.size);
      byId('confirmar-carrito').disabled = carrito.size === 0;
      byId('vaciar-carrito').disabled = carrito.size === 0;
    }
    byId('vaciar-carrito').addEventListener('click', () => {
      carrito.clear(); renderCarrito(); tell('carrito-status', 'Carrito vacío.');
    });
    byId('confirmar-carrito').addEventListener('click', async () => {
      if (carrito.size === 0) return;
      const items = [...carrito.values()].map(({ productoId, cantidadKg }) =>
        ({ productoId, cantidad_kg: cantidadKg }));
      try {
        const result = await request('/reservas/carrito', {
          method: 'POST', body: JSON.stringify({ items })
        });
        carrito.clear(); renderCarrito();
        tell('carrito-status', `Compra confirmada en ${result.pedidos.length} pedido(s), uno por agricultor.`);
        await buscar(); await cargarPedidos();
        byId('cuenta-panel').scrollIntoView({ behavior: 'smooth', block: 'start' });
      } catch (error) { tell('carrito-status', error.message); }
    });
    byId('buscar-form').addEventListener('submit', (event) => { event.preventDefault(); buscar(); });
    byId('catalog-search-form').addEventListener('submit', (event) => { event.preventDefault(); buscar(); });
    byId('catalog-query').addEventListener('input', () => {
      window.clearTimeout(window.catalogSearchTimer);
      window.catalogSearchTimer = window.setTimeout(buscar, 250);
    });
    byId('limpiar').addEventListener('click', () => { byId('buscar-form').reset(); buscar(); });
    byId('precios-form').addEventListener('submit', async (event) => {
      event.preventDefault();
      try { const p = await request(`/precios/regionales?categoria=${encodeURIComponent(formData(event.currentTarget).categoria)}`);
        if (p.transaccionesAnalizadas) {
          const chart = byId('price-chart'); chart.hidden = false;
          mostrarPrecioChart(chart, p.promedioPorKg, null, ['Promedio regional']);
          tell('precio-status', `Promedio: ${formatCop(p.promedioPorKg)}/kg, a partir de ${p.transaccionesAnalizadas} transacción(es).`);
        } else { byId('price-chart').hidden = true; tell('precio-status', 'Aún no hay transacciones completadas para calcular un promedio.'); }
      } catch (error) { tell('precio-status', error.message); }
    });
    byId('history-button').addEventListener('click', () => {
      tell('precio-status', 'El historial de cambios requiere datos de varios períodos; el servicio actual solo entrega el promedio acumulado disponible.');
      byId('precios-form').scrollIntoView({ behavior: 'smooth', block: 'center' });
    });
    const actividadVistas = {
      favoritos: ['Mis favoritos', 'Tus cosechas guardadas para volver a ellas cuando quieras.'],
      notificaciones: ['Mensajes directos', 'Contactos de personas interesadas en tus cosechas.'],
      pedidos: ['Pedidos y reservas', 'Consulta el avance de tus compras y entregas.']
    };
    function abrirActividad(tipo) {
      const vistaCambio = vistaActividad !== tipo;
      vistaActividad = tipo;
      const [titulo, descripcion] = actividadVistas[tipo] || actividadVistas.notificaciones;
      byId('actividad-titulo').textContent = titulo;
      byId('actividad-descripcion').textContent = descripcion;
      if (vistaCambio) {
        byId('actividad').replaceChildren();
        tell('actividad-status', 'Cargando…');
      }
      actualizarPaneles();
      if (location.hash !== '#actividad-vista') location.hash = '#actividad-vista';
      if (vistaCambio) window.scrollTo({ top: 0, behavior: 'smooth' });
    }
    function cerrarActividad() {
      vistaActividad = null;
      byId('actividad').replaceChildren();
      actualizarPaneles();
      const panel = rol === 'AGRICULTOR' ? '#agricultor-panel' : '#cuenta-panel';
      if (rol === 'AGRICULTOR') {
        document.querySelectorAll('#agricultor-panel .dashboard-sidebar a').forEach((item) => item.removeAttribute('aria-current'));
        document.querySelector('#agricultor-panel .dashboard-sidebar a[href="#agricultor-panel"]')?.setAttribute('aria-current', 'page');
      }
      if (location.hash !== panel) location.hash = panel;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
    function mostrarEstadoVacio(contenedor, texto) {
      const empty = document.createElement('p');
      empty.className = 'empty-state';
      empty.textContent = texto;
      contenedor.replaceChildren(empty);
    }
    byId('ver-favoritos').addEventListener('click', async () => {
      abrirActividad('favoritos');
      try {
        const items = await request('/favoritos');
        const cards = items.map((favorite) => {
          const card = document.createElement('article'); card.className = 'card activity-card favorite-card';
          const eyebrow = document.createElement('span'); eyebrow.className = 'eyebrow'; eyebrow.textContent = favorite.categoria || 'COSECHA LOCAL';
          const title = document.createElement('h3'); title.textContent = favorite.nombre;
          const detail = document.createElement('p'); detail.textContent = favorite.municipio || 'Valle del Cauca';
          card.append(eyebrow, title, detail); return card;
        });
        if (cards.length) byId('actividad').replaceChildren(...cards);
        else mostrarEstadoVacio(byId('actividad'), 'Aún no guardas cosechas. Cuando encuentres una que te guste, podrás verla aquí.');
        tell('actividad-status', `${items.length} cosecha(s) guardada(s).`);
      } catch (error) { tell('actividad-status', error.message); }
    });
    byId('ver-notificaciones').addEventListener('click', cargarMensajes);
    byId('activity-back').addEventListener('click', cerrarActividad);
    async function cargarMensajes() {
      abrirActividad('notificaciones');
      try {
        const items = await request('/notificaciones/mias');
        const contacts = rol === 'AGRICULTOR' ? items.filter((item) => item.tipo === 'CONTACTO') : items;
        const cards = contacts.map((item) => {
          const card = document.createElement('article'); card.className = 'card activity-card message-card';
          const top = document.createElement('div'); top.className = 'message-card-top';
          const badge = document.createElement('span'); badge.className = 'badge'; badge.textContent = item.tipo === 'CONTACTO' ? 'CONTACTO DE COMPRADOR' : item.tipo;
          const unread = document.createElement('span'); unread.className = item.leida ? 'message-read' : 'message-unread'; unread.textContent = item.leida ? 'Leído' : 'Nuevo';
          const message = document.createElement('p'); message.textContent = item.mensaje;
          const date = document.createElement('time'); date.className = 'message-date';
          if (item.creadaEn) { date.dateTime = item.creadaEn; date.textContent = new Intl.DateTimeFormat('es-CO', { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(item.creadaEn)); }
          top.append(badge, unread); card.append(top, message); if (date.textContent) card.append(date); return card;
        });
        if (cards.length) byId('actividad').replaceChildren(...cards);
        else mostrarEstadoVacio(byId('actividad'), rol === 'AGRICULTOR' ? 'Aún no tienes contactos. Cuando alguien pregunte por una cosecha, aparecerá aquí.' : 'No tienes mensajes pendientes por ahora.');
        tell('actividad-status', `${contacts.length} mensaje(s) en tu bandeja.`);
      } catch (error) { tell('actividad-status', error.message); }
    }
    async function cargarPedidos() {
      abrirActividad('pedidos');
      try {
        const path = rol === 'AGRICULTOR' ? '/reservas/pendientes' : '/reservas/mias';
        const orders = await request(path);
        const cards = orders.map((order) => {
          const card = document.createElement('article'); card.className = 'card activity-card order-card';
          const title = document.createElement('h3'); title.textContent = `Pedido #${order.id} · ${order.estado}`;
          const description = document.createElement('p');
          description.textContent = order.productos.map((p) => `${p.nombre}: ${p.cantidadKg} kg`).join(' · ');
          card.append(title, description);
          const actions = document.createElement('div'); actions.className = 'actions';
          if (rol === 'AGRICULTOR' && order.estado === 'PENDIENTE') {
            const button = document.createElement('button'); button.textContent = 'Confirmar reserva';
            button.onclick = async () => { try { await request(`/reservas/${order.id}/confirmar`, { method: 'POST' }); tell('actividad-status', 'Reserva confirmada.'); await cargarPedidos(); } catch (error) { tell('actividad-status', error.message); } };
            actions.append(button);
          } else if (rol === 'AGRICULTOR' && order.estado === 'CONFIRMADO') {
            const button = document.createElement('button'); button.textContent = 'Iniciar preparación';
            button.onclick = async () => { try { await request(`/reservas/${order.id}/preparar`, { method: 'POST' }); tell('actividad-status', 'Preparación iniciada.'); await cargarPedidos(); } catch (error) { tell('actividad-status', error.message); } };
            actions.append(button);
          } else if (rol === 'AGRICULTOR' && order.estado === 'PREPARANDO') {
            const button = document.createElement('button'); button.textContent = 'Programar despacho';
            button.onclick = async () => {
              const today = new Intl.DateTimeFormat('en-CA', { timeZone: 'America/Bogota' }).format(new Date());
              const fecha = prompt('Fecha (AAAA-MM-DD):', today); if (!fecha) return;
              const franjaHoraria = prompt('Franja horaria:'); if (!franjaHoraria) return;
              const ruta = prompt('Ruta (opcional):') || '';
              try { await request('/despachos', { method: 'POST', body: JSON.stringify({ pedidoId: order.id, fecha_programada: fecha, franjaHoraria, ruta }) }); tell('actividad-status', 'Despacho programado.'); await cargarPedidos(); } catch (error) { tell('actividad-status', error.message); }
            };
            actions.append(button);
          } else if (rol === 'AGRICULTOR' && order.estado === 'EN_DESPACHO') {
            const button = document.createElement('button'); button.textContent = 'Marcar salida a ruta';
            button.onclick = async () => { try { await request(`/despachos/${order.id}/en-ruta`, { method: 'PATCH' }); tell('actividad-status', 'Salida a ruta registrada.'); await cargarPedidos(); } catch (error) { tell('actividad-status', error.message); } };
            actions.append(button);
          } else if (rol === 'AGRICULTOR' && order.estado === 'EN_RUTA') {
            const button = document.createElement('button'); button.textContent = 'Marcar entrega completada';
            button.onclick = async () => { try { await request(`/despachos/${order.id}/entregado`, { method: 'PATCH' }); tell('actividad-status', 'Entrega registrada y precio agregado a las estadísticas.'); await cargarPedidos(); } catch (error) { tell('actividad-status', error.message); } };
            actions.append(button);
          } else if (rol === 'COMPRADOR') {
            const button = document.createElement('button'); button.className = 'secondary'; button.textContent = 'Ver trazabilidad';
            button.onclick = async () => { try { const events = await request(`/reservas/${order.id}/trazabilidad`); description.textContent += ` · ${events.map((event) => event.estado).join(' → ')}`; } catch (error) { tell('actividad-status', error.message); } };
            actions.append(button);
          }
          card.append(actions); return card;
        });
        byId('actividad').replaceChildren(...cards);
        if (orders.length === 0) mostrarEstadoVacio(byId('actividad'), 'Aún no hay reservas o pedidos. Cuando hagas una reserva aparecerá aquí.');
        tell('actividad-status', `${orders.length} pedido(s) encontrado(s).`);
      } catch (error) { tell('actividad-status', error.message); }
    }
    byId('ver-pedidos').addEventListener('click', cargarPedidos);
    window.setInterval(() => {
      if (token && rol === 'COMPRADOR' && vistaActividad === 'pedidos') cargarPedidos();
    }, 15000);
    byId('reporte-form').addEventListener('submit', async (event) => {
      event.preventDefault();
      await cargarReporteAdmin();
    });
    async function cargarReporteAdmin() {
      const fields = byId('reporte-form').elements;
      if (!fields.namedItem('desde').value || !fields.namedItem('hasta').value) {
        const hasta = todayInBogota();
        const inicio = new Date(`${hasta}T12:00:00-05:00`);
        inicio.setDate(inicio.getDate() - 29);
        fields.namedItem('desde').value = inicio.toISOString().slice(0, 10);
        fields.namedItem('hasta').value = hasta;
      }
      const { desde, hasta } = formData(byId('reporte-form'));
      try {
        const report = await request(`/admin/reportes/actividad?desde=${encodeURIComponent(desde)}&hasta=${encodeURIComponent(hasta)}`);
        byId('admin-user-count').textContent = String(report.usuariosRegistrados);
        byId('admin-harvest-count').textContent = String(report.ofertasPublicadas);
        byId('admin-farmer-count').textContent = '—';
        byId('admin-client-count').textContent = '—';
        tell('reporte-status', `Del ${report.desde} al ${report.hasta}: ${report.usuariosRegistrados} usuarios, ${report.ofertasPublicadas} cosechas y ${report.contactosRegistrados} contactos.`);
      } catch (error) { tell('reporte-status', error.message); }
    }
    document.querySelectorAll('[data-dashboard-action]').forEach((link) => link.addEventListener('click', async (event) => {
      event.preventDefault();
      document.querySelectorAll('#agricultor-panel .dashboard-sidebar a').forEach((item) => item.removeAttribute('aria-current'));
      link.setAttribute('aria-current', 'page');
      if (link.dataset.dashboardAction === 'pedidos') await cargarPedidos();
      else await cargarMensajes();
    }));
    document.querySelectorAll('#agricultor-panel .dashboard-sidebar a:not([data-dashboard-action])').forEach((link) => link.addEventListener('click', () => {
      document.querySelectorAll('#agricultor-panel .dashboard-sidebar a').forEach((item) => item.removeAttribute('aria-current'));
      link.setAttribute('aria-current', 'page');
    }));
    window.addEventListener('hashchange', () => {
      if (vistaActividad && location.hash !== '#actividad-vista') {
        vistaActividad = null;
        actualizarPaneles();
      }
    });
    actualizarPaneles();
