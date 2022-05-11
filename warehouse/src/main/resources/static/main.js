function sendEdit(id) {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let amount = document.getElementById('amount').value;
    const item = {name, description, amount};
    const requestOptions = {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(item)
    };

    fetch('/inventory/edit/' + id, requestOptions)
        .then(response => response.text());
}