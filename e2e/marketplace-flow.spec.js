const { test, expect } = require('@playwright/test');
const { randomUUID } = require('node:crypto');

test('agricultor publica una cosecha y comprador reserva desde el navegador', async ({ page }) => {
  const suffix = randomUUID().replaceAll('-', '').slice(0, 12);
  const farmerEmail = `agricultor-${suffix}@example.com`;
  const buyerEmail = `comprador-${suffix}@example.com`;
  const password = 'ClaveSegura2026';
  const productName = `Mango E2E ${suffix}`;
  const farmName = `Finca E2E ${suffix}`;

  await page.goto('/');
  await page.locator('#registro-form [name="nombre"]').fill(`Agricultor ${suffix}`);
  await page.locator('#registro-form [name="correo"]').fill(farmerEmail);
  await page.locator('#registro-form [name="contrasena"]').fill(password);
  await page.locator('#registro-form [name="cedula"]').fill(suffix);
  await page.locator('#registro-form [name="telefono"]').fill('3001234567');
  await page.locator('#registro-form [name="ubicacion_valle"]').fill('Dagua');
  await page.locator('#registro-form button').click();
  await expect(page.locator('#auth-status')).toContainText('Cuenta creada.');

  await page.locator('#login-form [name="correo"]').fill(farmerEmail);
  await page.locator('#login-form [name="contrasena"]').fill(password);
  await page.locator('#login-form button').click();
  await expect(page.locator('#auth-status')).toHaveText('Sesión iniciada como AGRICULTOR.');

  await page.locator('#finca-form [name="nombre"]').fill(farmName);
  await page.locator('#finca-form [name="municipio"]').fill('Dagua');
  await page.locator('#finca-form [name="direccion"]').fill('Vereda de prueba');
  await page.locator('#finca-form button').click();
  await expect(page.locator('#farm-status')).toHaveText('Finca registrada correctamente.');
  await page.locator('#parcela-select').selectOption({
    label: `${farmName} · GENERAL · Dagua`,
  });

  await page.locator('#producto-form [name="nombre"]').fill(productName);
  await page.locator('#producto-form [name="categoria"]').fill('Frutas');
  await expect(page.locator('#producto-form [name="municipio"]')).toHaveValue('Dagua');
  await page.locator('#producto-form [name="cantidadKg"]').fill('150');
  await page.locator('#producto-form [name="precioPorKg"]').fill('2000');
  await page.locator('#producto-form [name="fecha_cosecha"]')
    .fill(new Date(Date.now() + 86_400_000).toISOString().slice(0, 10));
  await page.locator('#producto-form button').click();
  await expect(page.locator('#product-status')).toHaveText('Oferta publicada correctamente.');
  await expect(page.locator('#catalogo article').filter({ hasText: productName })).toBeVisible();

  await page.locator('#salir').click();
  await page.locator('#registro-form [name="tipo"]').selectOption('COMPRADOR');
  await page.locator('#registro-form [name="nombre"]').fill(`Comprador ${suffix}`);
  await page.locator('#registro-form [name="correo"]').fill(buyerEmail);
  await page.locator('#registro-form [name="contrasena"]').fill(password);
  await page.locator('#registro-form [name="cedula"]').fill(`${suffix}B`);
  await page.locator('#registro-form [name="telefono"]').fill('3001234568');
  await page.locator('#registro-form button').click();
  await expect(page.locator('#auth-status')).toContainText('Cuenta creada.');

  await page.locator('#login-form [name="correo"]').fill(buyerEmail);
  await page.locator('#login-form [name="contrasena"]').fill(password);
  await page.locator('#login-form button').click();
  await expect(page.locator('#auth-status')).toHaveText('Sesión iniciada como COMPRADOR.');

  const offer = page.locator('#catalogo article').filter({ hasText: productName });
  await expect(offer).toBeVisible();
  await offer.getByRole('button', { name: 'Reservar' }).click();
  const reservationForm = offer.locator('.product-action-form');
  await reservationForm.locator('[name="cantidad"]').fill('101');
  await reservationForm.getByRole('button', { name: 'Confirmar reserva' }).click();
  await expect(page.locator('#catalog-status'))
    .toHaveText('Reserva creada. El agricultor debe confirmarla.');
  await expect(page.locator('#catalogo article').filter({ hasText: productName }))
    .toContainText('49 kg disponibles');
});
