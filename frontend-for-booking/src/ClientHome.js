import React, { useState, useEffect } from "react";
import axiosInstance from "./axios";
import {
    Grid,
    Button,
    TextField,
    Card,
    CardContent,
    CardMedia,
    Typography,
    ListItem,
    ListItemIcon,
    ListItemText,
    Avatar,
    List,
} from "@mui/material";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import ClientDetails from "./ClientDetails";

function ClientHome() {
    const [clientDetails, setClientDetails] = useState(null);
    const [location, setLocation] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [guests, setGuests] = useState(1);
    const [results, setResults] = useState([]);
    const [message, setMessage] = useState("");
    const [selectedHotel, setSelectedHotel] = useState(null);
    const [availableRooms, setAvailableRooms] = useState([]);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [roomRate, setRoomRate] = useState(null);

    // Obține detalii despre client
    useEffect(() => {
        const clientId = localStorage.getItem("userId");
        if (clientId) {
            axiosInstance.get(`/users/${clientId}`)
                .then(res => {
                    setClientDetails(res.data);
                })
                .catch(error => {
                    console.error("Error fetching client details:", error);
                });
        }
    }, []);

    // Caută hoteluri pe baza locației, a datelor de începere și de sfârșit și a numărului de oaspeți
    const searchHotels = async () => {
        try {
            const response = await axiosInstance.get(`/hotels/location/${location}`, {
                params: {
                    startDate,
                    endDate,
                    guests,
                },
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
            setMessage("No hotels found in the specified location for the selected date range and guest count.");
            setResults([]);
        }
    };

    // Obține camerele disponibile pentru hotelul selectat
    const fetchAvailableRooms = async (hotelId) => {
        try {
            const response = await axiosInstance.get(`/rooms/hotel/${hotelId}`);
            const roomsData = response.data;
            setAvailableRooms(roomsData);
            // Dacă există camere disponibile, setează roomRate pentru prima cameră
            if (roomsData.length > 0) {
                setRoomRate(roomsData[0].pricePerNight);
            }
        } catch (error) {
            console.error("Error fetching rooms:", error);
        }
    };

    // Gestionează evenimentul de clic pe hotel
    const handleHotelClick = async (hotel) => {
        setSelectedHotel(hotel);
        // Obține camerele disponibile pentru hotelul selectat
        await fetchAvailableRooms(hotel.id);
    };

    // Gestionează evenimentul de selecție a camerei
    const handleRoomSelection = (room) => {
        setSelectedRoom(room);
        setRoomRate(room.pricePerNight);
    };

    // Calculează costul total pentru rezervare
    const calculateTotalCost = (startDate, endDate, roomRate) => {
        // Calculează numărul de nopți dintre startDate și endDate
        const nights = Math.ceil((new Date(endDate) - new Date(startDate)) / (1000 * 60 * 60 * 24));
        // Asigură-te că numărul de nopți este cel puțin 1
        const validNights = nights > 0 ? nights : 1;
        // Calculează costul total pe baza numărului de nopți și a roomRate
        const totalCost = validNights * roomRate;
        return totalCost;
    };

    // Gestionează evenimentul de trimitere a rezervării
    const handleBookingSubmit = async () => {
        const userId = localStorage.getItem("userId");
        const bookingData = {
            userId,
            hotelId: selectedHotel.id,
            roomId: selectedRoom ? selectedRoom.id : null, // ID-ul camerei selectate
            startDate,
            endDate,
            bookingDate: new Date().toISOString().slice(0, 10),
            bookingTime: new Date().toLocaleTimeString(),
            totalCost: calculateTotalCost(startDate, endDate, roomRate),
        };

        console.log("Booking data:", bookingData);

        try {
            const response = await axiosInstance.post("/bookings/create", bookingData);
            console.log("Booking created successfully:", response.data);
        } catch (error) {
            console.error("Error creating booking:", error);
        }
    };

    return (
        <div>
            {/* AppBar with navigation options */}
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" style={{ flexGrow: 1 }}>
                        Client Dashboard
                    </Typography>
                </Toolbar>
            </AppBar>

            {/* Client details */}
            {clientDetails && <ClientDetails client={clientDetails} />}

            {/* Search form for hotels */}
            <Grid container spacing={2} style={{ marginTop: "20px" }}>
                <Grid item xs={12} sm={3}>
                    <TextField
                        fullWidth
                        label="Location"
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        variant="outlined"
                    />
                </Grid>
                <Grid item xs={12} sm={3}>
                    <TextField
                        fullWidth
                        label="Check-in Date"
                        type="date"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
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
                        onChange={(e) => setEndDate(e.target.value)}
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
                        onChange={(e) => setGuests(parseInt(e.target.value))}
                        variant="outlined"
                        InputLabelProps={{ shrink: true }}
                    />
                </Grid>
                <Grid item xs={12}>
                    <Button variant="contained" color="primary" onClick={searchHotels}>
                        Search
                    </Button>
                </Grid>
            </Grid>

            {/* Display hotel search results or a message if no results */}
            {results.length > 0 ? (
                <Grid container spacing={2} style={{ marginTop: "20px" }}>
                    {results.map((hotel) => (
                        <Grid item xs={12} sm={6} md={4} key={hotel.id}>
                            <Card onClick={() => handleHotelClick(hotel)}>
                                <CardMedia
                                    component="img"
                                    height="140"
                                    image={hotel.image}
                                    alt={hotel.name}
                                />
                                <CardContent>
                                    <Typography gutterBottom variant="h5">
                                        {hotel.name}
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary">
                                        {hotel.description}
                                    </Typography>
                                </CardContent>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            ) : (
                message && <p>{message}</p>
            )}

            {/* Display selected hotel details and booking form */}
            {selectedHotel && (
                <div>
                    <ListItem>
                        <ListItemIcon>
                            <Avatar>{"N"}</Avatar>
                        </ListItemIcon>
                        <ListItemText primary={`Name: ${selectedHotel.name}`} />
                    </ListItem>
                    <ListItem>
                        <ListItemIcon>
                            <Avatar>{"D"}</Avatar>
                        </ListItemIcon>
                        <ListItemText primary={`Description: ${selectedHotel.description}`} />
                    </ListItem>
                    <ListItem>
                        <ListItemIcon>
                            <Avatar>{"L"}</Avatar>
                        </ListItemIcon>
                        <ListItemText primary={`Location: ${selectedHotel.location}`} />
                    </ListItem>
                    <ListItem>
                        <ListItemIcon>
                            <Avatar>{"R"}</Avatar>
                        </ListItemIcon>
                        <ListItemText primary={`Number of Rooms: ${selectedHotel.numberOfRooms}`} />
                    </ListItem>

                    {/* Display available rooms for the selected hotel */}
                    {availableRooms.length > 0 && (
                        <div>
                            <Typography variant="h6">Available Rooms:</Typography>
                            <List>
                                {availableRooms.map((room) => (
                                    <ListItem button key={room.id} onClick={() => handleRoomSelection(room)}>
                                        <ListItemText primary={`Room Type: ${room.type}`} />
                                        <ListItemText secondary={`Price Per Night: $${room.pricePerNight}`} />
                                    </ListItem>
                                ))}
                            </List>
                        </div>
                    )}

                    {/* Booking form */}
                    <Grid container spacing={2} style={{ marginTop: "20px" }}>
                        <Grid item xs={12} sm={4}>
                            <TextField
                                fullWidth
                                label="Check-in Date"
                                type="date"
                                value={startDate}
                                onChange={(e) => setStartDate(e.target.value)}
                                variant="outlined"
                                InputLabelProps={{ shrink: true }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={4}>
                            <TextField
                                fullWidth
                                label="Check-out Date"
                                type="date"
                                value={endDate}
                                onChange={(e) => setEndDate(e.target.value)}
                                variant="outlined"
                                InputLabelProps={{ shrink: true }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={4}>
                            <TextField
                                fullWidth
                                label="Number of Guests"
                                type="number"
                                value={guests}
                                onChange={(e) => setGuests(parseInt(e.target.value))}
                                variant="outlined"
                                InputLabelProps={{ shrink: true }}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={handleBookingSubmit}
                            >
                                Book Now
                            </Button>
                        </Grid>
                    </Grid>
                </div>
            )}
        </div>
    );
}

export default ClientHome;
