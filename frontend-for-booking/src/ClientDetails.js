import React from "react";

import {Avatar, List, ListItem, ListItemIcon, ListItemText} from "@mui/material";

class ClientDetails extends React.Component {
    constructor(props) {
        super(props)
    };


    render() {
        return (
            <React.Fragment>
                <ListItem key={"details"+this.props.client.id}>
                    <ListItemIcon>
                        <Avatar>{"U"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={this.props.client.username}/>
                </ListItem>
                <ListItem key={this.props.client.id}>
                    <ListItemIcon>
                        <Avatar>{"P"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={this.props.client.password}/>
                </ListItem>
    
            </React.Fragment>
        )
    }
}

export default ClientDetails;