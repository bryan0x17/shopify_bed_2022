import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ItemList from './ItemList';
import ItemEdit from './ItemEdit';

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={ItemList}/>
            <Route path='/:id' component={ItemEdit}/>
            <Route path='/new' component={ItemEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;