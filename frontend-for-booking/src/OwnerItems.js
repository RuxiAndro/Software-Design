import React from "react";
import {Avatar, ListItem, ListItemIcon, ListItemText} from "@mui/material";
import OwnerDetails from "./OwnerDetails";
import Button from "@mui/material/Button";
import {Edit} from "@mui/icons-material";

class OwnerItems extends React.Component {
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
                <ListItem key={this.props.owner.id}>

                    <ListItemIcon>
                        <Avatar>{"O"}</Avatar>
                    </ListItemIcon>

                    <ListItemText
                        primary={this.props.owner.id + " " + this.props.owner.name}/>
                    <br/>
                    <Button onClick={() => this.displayDetails()}>
                        <Edit />DETAILS
                    </Button>
                    <div>
                        {this.state.displayDetails ? <OwnerDetails owner={this.props.owner} /> : null} 
                    </div>

                </ListItem>
            </React.Fragment>
        )
    }
}
//Dacă displayDetails este true, se afișează componenta OwnerDetails, 
//care ar putea conține detalii suplimentare despre proprietar, altfel componenta OwnerDetails nu este afișată.
export default OwnerItems;