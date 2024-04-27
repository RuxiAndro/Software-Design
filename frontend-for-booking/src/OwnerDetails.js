import React from "react";

import {Avatar, List, ListItem, ListItemIcon, ListItemText} from "@mui/material";
import OwnerHotelsList from "./OwnerHotelsList";

class OwnerDetails extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            selectedHotelId: null 
        };
    };

    
    
    render() {
        
        return (
            <React.Fragment>
                <ListItem key={"details"+this.props.owner.id}>
                    <ListItemIcon>
                        <Avatar>{"U"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={this.props.owner.username}/>
                </ListItem>
                

                <OwnerHotelsList
                ownerId={this.props.owner.id}
                onHotelClick={this.props.onHotelClick}
                />
                

               
           
            </React.Fragment>
        )
    }
}

export default OwnerDetails;