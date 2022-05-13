async function getDeleted() {
  const response = await fetch("/inventory/deleted");
  const data = await response.json();
  renderInventoryTable(data);
}

function renderInventoryTable(data) {
  const tableElement = document.getElementById("table");
  tableElement.innerHTML = "";
  if (!data.length || !Array.isArray(data)) {
    tableElement.append(
      (document.createElement("p").innerText = "No items to display")
    );
  } else {
    data.forEach((item) => {
      const row = document.createElement("tr");
      tableElement.appendChild(row);
      row.innerHTML = `
          <td>${item.name}</td>
          <td>${item.description}</td>
          <td>${item.deletionComments}</td>
          <td>
              <button class="btn btn-primary" onclick="restoreItem(${item.id})">Restore</button>
          </td>`;
    });
  }
}

async function restoreItem(id) {
  const requestOptions = {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
  };
  const response = await fetch("/inventory/deleted/" + id, requestOptions);
  if (response.ok) {
    window.alert("Item restored successfully");
    await getDeleted();
  } else {
    window.alert("Item could not be restored");
  }
}

getDeleted();
