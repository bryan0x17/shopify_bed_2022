async function getInventory() {
  const response = await fetch("/inventory");
  const data = await response.json();
  renderInventoryTable(data);
};

function renderInventoryTable(data) {
  const tableElement = document.getElementById("table");
  if (!data.length || !Array.isArray(data)) {
    tableElement.append(
      (document.createElement("p").innerText = "No items to display")
    );
  } else {
    data.forEach((item) => {
      tableElement.innerHTML = `
        <tr>
            <td>${item.name}</td>
            <td>${item.description}</td>
            <td>${item.amount}</td>
            <td>
                <button onclick="editItem(${item.id})">Edit</button>
                <button onclick="deleteItem(${item.id})">Delete</button>
            </td>
        </tr>`;
    });
  }
}

getInventory();