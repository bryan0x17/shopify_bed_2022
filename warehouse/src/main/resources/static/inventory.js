async function getInventory() {
  const response = await fetch("/inventory");
  const data = await response.json();
  renderInventoryTable(data);
}

function renderInventoryTable(data) {
  const tableElement = document.getElementById("table");
  if (!data.length || !Array.isArray(data)) {
    tableElement.append(
      (document.createElement("p").innerText = "No items to display")
    );
  } else {
    data.forEach((item) => {
      tableElement.innerHTML = `
        <tr id="${item.id}">
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

async function editItem(id) {
  const row = document.getElementById(id);
  const item = await getInventoryItem(id);
  console.log(item);
  if (item) {
    row.innerHTML = `
        <td>
            <input type="text" name="name" id="name" value="${item.name}"></td>
        <td>
            <input type="text" name="description" id="description" value="${item.description}"></td>
        <td>
            <input type="number" name="amount" id="amount" value="${item.amount}"></td>
        <td>
            <button onclick="saveItem(${item.id}">Save</button>
        </td>
        `;
  } else {
      window.alert("Item can't be edited");
  }
  
}

async function getInventoryItem(id) {
    return fetch('/inventory/' + id)
        .then(response => response.json());
}

getInventory();
