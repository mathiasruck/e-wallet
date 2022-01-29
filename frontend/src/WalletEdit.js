import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavBar';

class WalletEdit extends Component {

    emptyItem = {
        ownerFullName: '',
        balance: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const wallet = await (await fetch(`/wallets/${this.props.match.params.id}`)).json();
            this.setState({item: wallet});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/wallets' + (item.id ? '/' + item.id : ''), {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
    });
    this.props.history.push('/wallets');
}

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit wallet' : 'Add wallet'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="ownerFullName">Owner Full Name</Label>
                        <Input type="text" name="ownerFullName" id="ownerFullName" defaultValue={item.ownerFullName || ''}
                               onChange={this.handleChange} autoComplete="ownerFullName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="balance">Current Balance</Label>
                        <Input type="text" name="balance" id="balance" defaultValue={item.balance || ''}
                               onChange={this.handleChange} autoComplete="balance"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/wallets">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(WalletEdit);