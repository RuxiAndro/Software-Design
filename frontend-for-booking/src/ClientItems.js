import React from "react";
import {Avatar, ListItem, ListItemIcon, ListItemText} from "@mui/material";
import ClientDetails from "./ClientDetails";
import Button from "@mui/material/Button";
import {Edit} from "@mui/icons-material";

class ClientItems extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            displayDetails: false, //initial detaliile nu sunt afisate
        }
    };

    displayDetails = () => {
        this.setState({
            displayDetails: !this.state.displayDetails //Această metodă este responsabilă pentru a schimba starea displayDetails între true și false. Ea este apelată atunci când utilizatorul dă clic pe butonul "DETAILS".
        })
    }

    render() { //returneaza reprezentarea vizuala a componentei
        return (
            <React.Fragment>
                <ListItem key={this.props.client.id}>

                    <ListItemIcon>
                        <Avatar>{"O"}</Avatar>
                    </ListItemIcon>

                    <ListItemText
                        primary={this.props.client.id + " " + this.props.client.name}/>
                    <br/>
                    <Button onClick={() => this.displayDetails()}>
                        <Edit />DETAILS
                    </Button>
                    <div>
                        {this.state.displayDetails ? <ClientDetails client={this.props.client} /> : null} 
                    </div>

                </ListItem>
            </React.Fragment>
        )
    }
}
//Dacă displayDetails este true, se afișează componenta OwnerDetails, 
//care ar putea conține detalii suplimentare despre proprietar, altfel componenta OwnerDetails nu este afișată.
export default ClientItems;