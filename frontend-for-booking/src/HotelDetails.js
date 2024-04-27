import React from "react";
import { Avatar, ListItem, ListItemIcon, ListItemText } from "@mui/material";

class HotelDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            newHotelData: {
                name: "",
                description: "",
                location: "",
                numberOfRooms: 0
            }
        };
    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        this.setState({
            newHotelData: {
                ...this.state.newHotelData,
                [name]: value
            }
        });
    };
    
    handleAddHotel = () => {
        const { name, description, location, numberOfRooms } = this.state.newHotelData;
        // Verificăm dacă toate câmpurile sunt completate
        if (name && description && location && numberOfRooms) {
            // Construim obiectul cu datele noului hotel
            const newHotel = {
                name: name,
                description: description,
                location: location,
                numberOfRooms: parseInt(numberOfRooms) // Conversie la număr întreg
            };
            // Apelăm funcția pentru adăugarea hotelului și transmitem obiectul cu datele noului hotel
            this.props.onAddHotel(newHotel);
            // Resetăm starea pentru datele noului hotel, pentru a șterge valorile din câmpurile de text
            this.setState({
                newHotelData: {
                    name: "",
                    description: "",
                    location: "",
                    numberOfRooms: 0
                }
            });
        } else {
            // Afisăm un mesaj de eroare în cazul în care nu sunt completate toate câmpurile
            alert("Toate câmpurile sunt obligatorii!");
        }
    };
    

    render() {
        const { name, description, location, numberOfRooms } = this.props.hotel;

        return (
            <React.Fragment>
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"N"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={`Name: ${name}`}
                    />
                </ListItem>
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"D"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={`Description: ${description}`}
                    />
                </ListItem>
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"L"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={`Location: ${location}`}
                    />
                </ListItem>
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"R"}</Avatar>
                    </ListItemIcon>
                    <ListItemText
                        primary={`Number of Rooms: ${numberOfRooms}`}
                    />
                </ListItem>
            </React.Fragment>
        );
    }
}

export default HotelDetails;
