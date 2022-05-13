async function getInventory() {
  const response = await fetch("/inventory");
  const data = await response.json();
  renderInventoryTable(data);
}

function renderInventoryTable(data) {
  const tableElement = document.getElementById("table");
  tableElement.innerHTML = '';
  if (!data.length || !Array.isArray(data)) {
    tableElement.append(
      (document.createElement("p").innerText = "No items to display")
    );
  } else {
    data.forEach((item) => {
      const row = document.createElement("tr");
      tableElement.appendChild(row);
      row.id = item.id;
      row.innerHTML = `
        <td>${item.name}</td>
        <td>${item.description}</td>
        <td>${item.amount}</td>
        <td>
            <button onclick="editItem(${item.id})">Edit</button>
            <button onclick="deleteItem(${item.id})">Delete</button>
        </td>`;
    });
  }
}

//Renders the row as inputs
async function editItem(id) {
  const row = document.getElementById(id);
  const item = await getInventoryItem(id);
  if (item) {
    row.innerHTML = `
        <td>
            <input type="text" name="name" id="name" value="${item.name}"></td>
        <td>
            <input type="text" name="description" id="description" value="${item.description}"></td>
        <td>
            <input type="number" name="amount" id="amount" value="${item.amount}"></td>
        <td>
            <button onclick="saveItem(${item.id})">Save</button>
        </td>
        `;
  } else {
    window.alert("Item can't be edited");
  }
}

//Grabs the inputs and sends the PUT request to update
async function saveItem(id) {
  const name = document.getElementById("name").value;
  const description = document.getElementById("description").value;
  const amount = document.getElementById("amount").value;
  const requestOptions = {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, description, amount }),
  };
  const response = await fetch("/inventory/" + id, requestOptions);
  if (response.ok) {
    window.alert("Item updated successfully");
    await getInventory();
  } else {
    window.alert("Item could not be updated");
  }
}

async function getInventoryItem(id) {
  return fetch("/inventory/" + id).then((response) => response.json());
}

//Generates 20 random example items to work with
async function generateExamples() {
  const tableElement = document.getElementById("table");
  tableElement.innerHTML = '<p>Generating examples...</p>';
  const names = [
    "Table",
    "Chair",
    "TV",
    "Couch",
    "Fridge",
    "Bed",
    "Desk",
    "Cupboard",
    "Dishwasher",
    "Home theatre",
  ];
  const descriptions = ["Oak", "Outdoor", "Premium", "Sale", "Made in Canada"];

  for (let i = 0; i < 20; i++) {
    const name = names[Math.floor(Math.random() * names.length)];
    const description =
      descriptions[Math.floor(Math.random() * descriptions.length)];
    const amount = Math.round(Math.random() * 100);
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, description, amount })
    };
    await fetch("/inventory", requestOptions);
  }
  getInventory();
}

//Renders the input for deletion comments
async function deleteItem(id) {
  const row = document.getElementById(id);
  const item = await getInventoryItem(id);
  row.innerHTML = `
        <td>${item.name}</td>
        <td>${item.description}</td>
        <td>${item.amount}</td>
        <td>
            <label for="comments">Comments</label>
            <input type="text" name="comments" id="comments">
            <button onclick="sendDelete(${id})">Delete</button>
        </td>`;
}

//Grabs the deletion comments and sends the DELETE request
async function sendDelete(id) {
  const comments = document.getElementById("comments").value;
  const response = await fetch("/inventory/" + id, {
    method: "DELETE",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ deletionComments: comments })
  });
  if (response.ok) {
    window.alert("Item deleted successfully");
    await getInventory();
  } else {
    window.alert("Item could not be deleted");
  }
  getInventory();
}

getInventory();
