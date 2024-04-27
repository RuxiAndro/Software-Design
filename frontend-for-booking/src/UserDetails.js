
import React from "react";

import {Avatar, List, ListItem, ListItemIcon, ListItemText} from "@mui/material";

class UserDetails extends React.Component {
    constructor(props) {
        super(props)
    };

    render() {
        return (
            <React.Fragment>
                <ListItem key={"details"+this.props.user.id}>
                    <ListItemIcon>
                        <Avatar>{"U"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={this.props.user.username}/>
                </ListItem>
                <ListItem key={this.props.user.id}>
                    <ListItemIcon>
                        <Avatar>{"P"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={this.props.user.password}/>
                </ListItem>
            </React.Fragment>
        )
    }
}

export default UserDetails;