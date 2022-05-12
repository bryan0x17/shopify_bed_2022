async function addNewItem() {
    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const amount = document.getElementById('amount').value;
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({name, description, amount})
    };
    const response = await fetch('/inventory', requestOptions);
    if (response.ok) {
        window.alert("Item added successfully");
        return false;
    } else {
        window.alert("Item could not be added");
    }
}

document.getElementById("submitButton").addEventListener("click", addNewItem);