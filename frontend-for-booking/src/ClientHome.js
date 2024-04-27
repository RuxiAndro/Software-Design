import React from "react";
import axiosInstance from "./axios";

import {Avatar, List, ListItem, ListItemIcon, ListItemText} from "@mui/material";
import ClientItems from "./ClientItems";
import ClientDetails from "./ClientDetails";

class ClientHome extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            clientDetails:null,
            clients: [],
        }
    };


 /*   componentDidMount() {//Poți folosi componentDidMount() pentru a inițializa starea componentei, pentru a face cereri către server pentru a obține date inițiale, pentru a conecta componente la alte servicii sau pentru a seta abonamente la evenimente.
        axiosInstance.get("/users/Users")
            .then(res => {
                const val = res.data;
                const clientsOnly = val.filter(user => user.role === 'CLIENT');
                this.setState({
                    clients: clientsOnly //va contine acum doar ownerii
                });
            })
            .catch(error => {
                console.log(error);
            });
    };*/
    componentDidMount(){
        const clientId=localStorage.getItem("userId");
        if(clientId){
            axiosInstance.get(`/users/${clientId}`)
            .then(res => {
                const clientDetails = res.data; 
                this.setState({ clientDetails });
            })
            .catch(error => {
                console.log(error);
            });
        }
    }

    render() {
        const {clientDetails}=this.state;
        return (
            <React.Fragment>
            {clientDetails ? (
                <React.Fragment>
                    <ClientDetails client={clientDetails} />
                 </React.Fragment>
                 ) : (
                    <p>Loading...</p>
                )}
            </React.Fragment>
        )
    }
}

export default ClientHome;