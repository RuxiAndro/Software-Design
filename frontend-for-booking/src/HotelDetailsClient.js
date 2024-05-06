import React from "react";
import axiosInstance from "./axios";
import { Avatar, ListItem, ListItemIcon, ListItemText, Button, TextField, Grid } from "@mui/material";

class HotelDetailsClient extends React.Component {
    constructor(props) {
        super(props);
        // Initialize state and hotel property
        this.state = {
            bookingDetails: {
                startDate: "",
                endDate: "",
                guests: 1
            }
        };
        this.hotel = props.location?.state?.hotel || null;

        console.log('Received hotel:', this.hotel);
    }

    handleBookingChange = (event) => {
        const { name, value } = event.target;
        this.setState(prevState => ({
            bookingDetails: {
                ...prevState.bookingDetails,
                [name]: value
            }
        }));
    };

    handleBookingSubmit = async () => {
        const { startDate, endDate, guests } = this.state.bookingDetails;
        const { id: hotelId } = this.props.hotel; // Get the hotel ID from props
        const userId = localStorage.getItem("userId");
        const roomId = 1; // You may need to modify this to choose the correct room based on booking details

        // Construct the booking request payload
        const bookingData = {
            startDate,
            endDate,
            guests
        };

        try {
            // Make a POST request to the /bookings/create endpoint
            const response = await axiosInstance.post("/bookings/create", bookingData, {
                params: {
                    hotelId,
                    roomId,
                    userId
                }
            });

            // Handle successful booking
            console.log("Booking created successfully:", response.data);
            // Provide feedback to the user or navigate to another page if desired
        } catch (error) {
            console.error("Error creating booking:", error);
            // Optionally, provide error feedback to the user
        }
    };

    render() {
        console.log('Props in HotelDetailsClient:', this.props);
       
       // const hotel = this.props.location && this.props.location.state ? this.props.location.state.hotel : {};
       // console.log('Received hotel:', hotel);
       const hotel = this.hotel;

        // Dacă datele hotelului nu sunt disponibile, poți seta valori implicite sau un comportament de fallback
        if (!hotel || Object.keys(hotel).length === 0) {
            return (
                <div>
                    <p>No hotel details available.</p>
                </div>
            );
        }

        // Destructure hotel properties
        const { name, description, location, numberOfRooms } = hotel;

        return (
            <React.Fragment>
                {/* Display hotel details */}
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"N"}</Avatar>
                    </ListItemIcon>
                    <ListItemText primary={`Name: ${name}`} />
                </ListItem>
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"D"}</Avatar>
                    </ListItemIcon>
                    <ListItemText primary={`Description: ${description}`} />
                </ListItem>
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"L"}</Avatar>
                    </ListItemIcon>
                    <ListItemText primary={`Location: ${location}`} />
                </ListItem>
                <ListItem>
                    <ListItemIcon>
                        <Avatar>{"R"}</Avatar>
                    </ListItemIcon>
                    <ListItemText primary={`Number of Rooms: ${numberOfRooms}`} />
                </ListItem>

                {/* Booking form */}
                <Grid container spacing={2} style={{ marginTop: '20px' }}>
                    {/* Booking form inputs */}
                    <Grid item xs={12} sm={4}>
                        <TextField
                            fullWidth
                            label="Check-in Date"
                            type="date"
                            name="startDate"
                            value={this.state.bookingDetails.startDate}
                            onChange={this.handleBookingChange}
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                        />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField
                            fullWidth
                            label="Check-out Date"
                            type="date"
                            name="endDate"
                            value={this.state.bookingDetails.endDate}
                            onChange={this.handleBookingChange}
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                        />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField
                            fullWidth
                            label="Number of Guests"
                            type="number"
                            name="guests"
                            value={this.state.bookingDetails.guests}
                            onChange={this.handleBookingChange}
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={this.handleBookingSubmit}
                        >
                            Book Now
                        </Button>
                    </Grid>
                </Grid>
            </React.Fragment>
        );
    }
}

export default HotelDetailsClient;
