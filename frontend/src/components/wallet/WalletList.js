import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../app/AppNavBar';
import { Link } from 'react-router-dom';
import CurrencyFormat from 'react-currency-format';

class WalletList extends Component {

    constructor(props) {
        super(props);
        this.state = { wallets: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/wallets')
            .then(response => response.json())
            .then(data => this.setState({ wallets: data }));
    }


    async remove(id) {
        await fetch(`/wallets/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedWallets = [...this.state.wallets].filter(i => i.id !== id);
            this.setState({ wallets: updatedWallets });
        });
    }

    render() {
        const { wallets, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const walletsList = wallets.map(wallet => {
            return <tr key={wallet.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{wallet.ownerFullName}</td>
                <td><CurrencyFormat value={wallet.balance} displayType={'text'} prefix={'€ '}
                    allowNegative={false} fixedDecimalScale={true} decimalScale={2} /></td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="success" tag={Link} to={"/wallets/" + wallet.id + "/balance/add"}>Add</Button>
                        <Button size="sm" color="warning" tag={Link} to={"/wallets/" + wallet.id + "/balance/withdraw"}>Withdraw</Button>
                        <Button size="sm" color="primary" tag={Link} to={"/wallets/" + wallet.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(wallet.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/wallets/new">Add Wallet</Button>
                    </div>
                    <br/>
                    <h3>Wallets</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="30%">Owner</th>
                                <th width="15%">Balacce</th>
                                <th width="55%">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {walletsList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default WalletList;