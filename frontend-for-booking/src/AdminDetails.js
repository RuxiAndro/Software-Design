import React from "react";

import {Avatar, List, ListItem, ListItemIcon, ListItemText} from "@mui/material";

class AdminDetails extends React.Component {
    constructor(props) {
        super(props)
    };


    render() {
        return (
            <React.Fragment>
                <ListItem key={"details"+this.props.admin.id}>
                    <ListItemIcon>
                        <Avatar>{"U"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={this.props.admin.username}/>
                </ListItem>
                <ListItem key={this.props.admin.id}>
                    <ListItemIcon>
                        <Avatar>{"P"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={this.props.admin.password}/>
                </ListItem>
    
            </React.Fragment>
        )
    }
}

export default AdminDetails;