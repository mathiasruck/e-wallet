import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import WalletList from './WalletList';
import WalletEdit from "./WalletEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/wallets' exact={true} component={WalletList}/>
            <Route path='/wallets/:id' component={WalletEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;