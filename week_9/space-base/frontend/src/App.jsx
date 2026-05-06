import { useEffect, useState } from 'react';

const productBaseUrl = 'http://localhost:8081';
const cartBaseUrl = 'http://localhost:8082';
const orderBaseUrl = 'http://localhost:8083';
const inventoryBaseUrl = 'http://localhost:8084';

export default function App() {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [selectedUserId, setSelectedUserId] = useState('user-001');
  const [statusMessage, setStatusMessage] = useState('');
  const [busy, setBusy] = useState(false);

  useEffect(() => {
    loadProducts();
  }, []);

  async function loadProducts() {
    const response = await fetch(`${productBaseUrl}/products`);
    const data = await response.json();
    setProducts(data);
  }

  async function loadCart(userId) {
    const response = await fetch(`${cartBaseUrl}/cart/${encodeURIComponent(userId)}`);
    const data = await response.json();
    setCart(data);
  }

  async function addToCart(product) {
    setBusy(true);
    setStatusMessage('Adding item to cart...');
    try {
      await fetch(`${cartBaseUrl}/cart/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId: selectedUserId,
          productId: product.id,
          productName: product.name,
          unitPrice: product.price,
          quantity: 1,
        }),
      });
      await loadCart(selectedUserId);
      setStatusMessage(`${product.name} added to cart.`);
    } finally {
      setBusy(false);
    }
  }

  async function checkout() {
    setBusy(true);
    setStatusMessage('Creating order...');
    try {
      const response = await fetch(`${orderBaseUrl}/checkout`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId: selectedUserId }),
      });
      const result = await response.json();
      setStatusMessage(`${result.status}: ${result.message || 'Order processed'}`);
      await loadCart(selectedUserId);
      await loadProducts();
    } finally {
      setBusy(false);
    }
  }

  async function inspectStock(productId) {
    const response = await fetch(`${inventoryBaseUrl}/stock/${productId}`);
    const data = await response.json();
    setStatusMessage(`${data.productName} stock: ${data.stock}`);
  }

  return (
    <div className="shell">
      <div className="hero">
        <div>
          <p className="eyebrow">Space-Based Architecture Demo</p>
          <h1>Flash Sale Control Panel</h1>
          <p className="subtitle">
            Product, cart, order, and inventory services all run independently on Redis as the shared data grid.
          </p>
        </div>

        <div className="user-box">
          <label htmlFor="userId">User ID</label>
          <input
            id="userId"
            value={selectedUserId}
            onChange={(event) => setSelectedUserId(event.target.value)}
            onBlur={() => loadCart(selectedUserId)}
          />
          <button type="button" onClick={() => loadCart(selectedUserId)} disabled={busy}>
            Load Cart
          </button>
        </div>
      </div>

      <div className="status-bar">{statusMessage || 'Ready.'}</div>

      <main className="grid">
        <section className="panel">
          <div className="panel-head">
            <h2>Products</h2>
            <button type="button" onClick={loadProducts} disabled={busy}>
              Refresh
            </button>
          </div>
          <div className="cards">
            {products.map((product) => (
              <article className="card" key={product.id}>
                <div>
                  <h3>{product.name}</h3>
                  <p>{product.description}</p>
                </div>
                <div className="meta-row">
                  <span>{Number(product.price).toLocaleString('vi-VN')} VND</span>
                  <span>Stock {product.stock}</span>
                </div>
                <div className="actions">
                  <button type="button" onClick={() => addToCart(product)} disabled={busy}>
                    Add to cart
                  </button>
                  <button type="button" onClick={() => inspectStock(product.id)} disabled={busy} className="ghost">
                    Inspect stock
                  </button>
                </div>
              </article>
            ))}
          </div>
        </section>

        <section className="panel">
          <div className="panel-head">
            <h2>Cart</h2>
            <button type="button" onClick={checkout} disabled={busy || cart.length === 0}>
              Checkout
            </button>
          </div>
          <div className="cart-list">
            {cart.length === 0 ? (
              <p className="empty">Cart is empty for {selectedUserId}.</p>
            ) : (
              cart.map((item, index) => (
                <div className="cart-item" key={`${item.productId}-${index}`}>
                  <div>
                    <strong>{item.productName}</strong>
                    <p>Quantity: {item.quantity}</p>
                  </div>
                  <span>{Number(item.unitPrice).toLocaleString('vi-VN')} VND</span>
                </div>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  );
}