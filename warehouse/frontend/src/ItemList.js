import React, { Component } from "react";
import { Button, ButtonGroup, Container, Table } from "reactstrap";
import AppNavbar from "./AppNavbar";
import { Link } from "react-router-dom";

class ItemList extends Component {
  constructor(props) {
    super(props);
    this.state = { items: [] };
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    fetch("/")
      .then((response) => response.json())
      .then((data) => this.setState({ items: data }));
  }

  async remove(id) {
    await fetch(`/${id}`, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    }).then(() => {
      let updatedItems = [...this.state.items].filter((i) => i.id !== id);
      this.setState({ items: updatedItems });
    });
  }

  render() {
    const { items, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const itemList = items.map((item) => {
      return (
        <tr key={item.id}>
          <td style={{ whiteSpace: "nowrap" }}>{item.name}</td>
          <td>{item.description}</td>
          <td>{item.amount}</td>
          <td>
            <ButtonGroup>
              <Button
                size="sm"
                color="primary"
                tag={Link}
                to={"/" + item.id}
              >
                Edit
              </Button>
              <Button
                size="sm"
                color="danger"
                onClick={() => this.remove(item.id)}
              >
                Delete
              </Button>
            </ButtonGroup>
          </td>
        </tr>
      );
    });

    return (
      <div>
        <AppNavbar />
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/new">
              Add Item
            </Button>
          </div>
          <h3>Items</h3>
          <Table className="mt-4">
            <thead>
              <tr>
                <th width="30%">Name</th>
                <th width="30%">Description</th>
                <th width="30%">Amount</th>
                <th width="40%">Actions</th>
              </tr>
            </thead>
            <tbody>{itemList}</tbody>
          </Table>
        </Container>
      </div>
    );
  }
}
export default ItemList;
