import React, { useState } from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Link } from "react-router-dom";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import axiosInstance from "./axios";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import { CardActionArea } from "@mui/material";

const HomePage = () => {
    const [location, setLocation] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [guests, setGuests] = useState(1);
    const [results, setResults] = useState([]);
    const [message, setMessage] = useState("");

    const searchHotels = async () => {
        try {
            const response = await axiosInstance.get(`/hotels/location/${location}`, {
                params: {
                    startDate: startDate,
                    endDate: endDate,
                    guests: guests
                }
            });
            const hotels = response.data;

            if (hotels.length > 0) {
                setResults(hotels);
                setMessage("");
            } else {
                setMessage("No hotels found in the specified location for the selected date range and guest count.");
                setResults([]);
            }
        } catch (error) {
            console.error("Error searching hotels by location:", error);
            console.error("HTTP Status Code:", error.response ? error.response.status : "Unknown");
            console.error("Error details:", error.response ? error.response.data : error.message);
            setResults([]);
            setMessage("No hotels found in the specified location for the selected date range and guest count.");
        }
    };

    const handleLocationChange = (event) => {
        setLocation(event.target.value);
    };

    const handleStartDateChange = (event) => {
        setStartDate(event.target.value);
    };

    const handleEndDateChange = (event) => {
        setEndDate(event.target.value);
    };

    const handleGuestsChange = (event) => {
        setGuests(event.target.value);
    };

    const renderHotelCards = () => {
        return results.map((hotel) => (
            <Grid item xs={12} sm={6} md={4} key={hotel.id}>
                <Card>
                    <CardActionArea>
                        <CardMedia
                            component="img"
                            height="140"
                            image={hotel.image}
                            alt={hotel.name}
                        />
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="div">
                                {hotel.name}
                            </Typography>
                            <Typography variant="body2" color="text.secondary">
                                {hotel.description}
                            </Typography>
                        </CardContent>
                    </CardActionArea>
                </Card>
            </Grid>
        ));
    };

    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" style={{ flexGrow: 1 }}>
                        Book Your Trip
                    </Typography>
                    <Button color="inherit">
                        <Link to="/login" style={{ textDecoration: 'none', color: 'inherit' }}>
                            Sign In
                        </Link>
                    </Button>
                    <Button color="inherit">
                        <Link to="/register" style={{ textDecoration: 'none', color: 'inherit' }}>
                            Sign Up
                        </Link>
                    </Button>
                </Toolbar>
            </AppBar>

            <div>
                <p>Start planning your next trip with our convenient booking system!</p>

                <Grid container spacing={2}>
                    <Grid item xs={12} sm={3}>
                        <TextField
                            fullWidth
                            label="Location"
                            value={location}
                            onChange={handleLocationChange}
                            variant="outlined"
                        />
                    </Grid>
                    <Grid item xs={12} sm={3}>
                        <TextField
                            fullWidth
                            label="Check-in Date"
                            type="date"
                            value={startDate}
                            onChange={handleStartDateChange}
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                        />
                    </Grid>
                    <Grid item xs={12} sm={3}>
                        <TextField
                            fullWidth
                            label="Check-out Date"
                            type="date"
                            value={endDate}
                            onChange={handleEndDateChange}
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                        />
                    </Grid>
                    <Grid item xs={12} sm={3}>
                        <TextField
                            fullWidth
                            label="Number of Guests"
                            type="number"
                            value={guests}
                            onChange={handleGuestsChange}
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                        />
                    </Grid>
                    <Grid item xs={12} sm={12}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={searchHotels}
                        >
                            Search
                        </Button>
                    </Grid>
                </Grid>

                {results.length > 0 ? (
                    <Grid container spacing={2} style={{ marginTop: '20px' }}>
                        {renderHotelCards()}
                    </Grid>
                ) : (
                    message && <p>{message}</p>
                )}
            </div>
        </div>
    );
};

export default HomePage;
