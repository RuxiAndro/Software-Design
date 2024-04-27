import React from "react";
import axiosInstance from "./axios";
import { Avatar, ListItem, ListItemIcon, ListItemText, Button } from "@mui/material";
import AdminDetails from "./AdminDetails";
import { Edit, Delete } from "@mui/icons-material";


class AdminItems extends React.Component {
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

    deleteUser = async () => {
        try {
          // Send a DELETE request to the server
          await axiosInstance.delete(`/users/delete/${this.props.admin.id}`);
          // Once the deletion is successful, remove the admin from the state
          this.props.onUserDelete(this.props.admin.id);
        } catch (error) {
          console.error("Error deleting user:", error);
        }
      }
      

    render() { //returneaza reprezentarea vizuala a componentei
        return (
            <React.Fragment>
                <ListItem key={this.props.admin.id}>

                    <ListItemIcon>
                        <Avatar>{"O"}</Avatar>
                    </ListItemIcon>

                    <ListItemText
                        primary={this.props.admin.id + " " + this.props.admin.name}/>
                    <br/>
                    <Button onClick={() => this.displayDetails()}>
                        <Edit />DETAILS
                    </Button>

                    <Button onClick={this.deleteUser} color="secondary" startIcon={<Delete />}>
                          Delete
                    </Button>

                    <div>
                        {this.state.displayDetails ? <AdminDetails admin={this.props.admin} /> : null} 
                    </div>

                </ListItem>
            </React.Fragment>
        )
    }
}
//Dacă displayDetails este true, se afișează componenta OwnerDetails, 
//care ar putea conține detalii suplimentare despre proprietar, altfel componenta OwnerDetails nu este afișată.
export default AdminItems;