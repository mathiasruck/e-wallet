import React, { Component } from 'react';
import './App.css';
import Home from '../Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import WalletList from '../wallet/WalletList';
import WalletEdit from "../wallet/WalletEdit";
import BalanceWithdraw from "../balance/BalanceWithdraw";
import BalanceAdd from "../balance/BalanceAdd";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/wallets' exact={true} component={WalletList}/>
            <Route path='/wallets/:id' exact={true} component={WalletEdit}/>
            <Route path='/wallets/:id/balance/add' exact={true} component={BalanceAdd}/>
            <Route path='/wallets/:id/balance/withdraw' exact={true} component={BalanceWithdraw}/>
          </Switch>
        </Router>
    )
  }
}

export default App;