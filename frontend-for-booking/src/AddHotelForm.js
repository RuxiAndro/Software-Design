import React from "react";

class AddHotelForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            location: "",
            numberOfRooms: ""
        };
    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        this.setState({ [name]: value });
    };

    onSave = () => {
        const { name, description, location, numberOfRooms } = this.state;

        const hotelData = {
            name: name,
            description: description,
            location: location,
            numberOfRooms: numberOfRooms
        };

        // Verificăm dacă toate câmpurile sunt completate
        if (name && description && location && numberOfRooms) {
            this.props.onAddHotel(hotelData);

            // Resetăm starea formularului pentru a șterge valorile din câmpurile de text
            this.setState({
                name: "",
                description: "",
                location: "",
                numberOfRooms: ""
            });
        } else {
            alert("Toate câmpurile sunt obligatorii!");
        }
    };

    render() {
        const { name, description, location, numberOfRooms } = this.state;
        return (
            <div>
                <input type="text" name="name" value={name} onChange={this.handleInputChange} placeholder="Hotel Name" />
                <input type="text" name="description" value={description} onChange={this.handleInputChange} placeholder="Description" />
                <input type="text" name="location" value={location} onChange={this.handleInputChange} placeholder="Location" />
                <input type="text" name="numberOfRooms" value={numberOfRooms} onChange={this.handleInputChange} placeholder="Number of Rooms" />
                <button onClick={this.onSave}>Add Hotel</button>
            </div>
        );
    }
}

export default AddHotelForm;
