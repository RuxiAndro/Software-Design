import React from "react";
import axiosInstance from "./axios";

import OwnerDetails from "./OwnerDetails";
import AddHotelForm from "./AddHotelForm";
import UpdateHotelForm from "./UpdateHotelForm";
import { List, ListItem, ListItemText } from "@mui/material";


class OwnerHome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            ownerDetails: null,
            hotels: [], // Lista de hoteluri disponibile
            selectedHotel: null,
        };
    }

    componentDidMount() {
      
        const ownerId = localStorage.getItem("userId");
        if (ownerId) {
            
            axiosInstance.get(`/users/${ownerId}`)
                .then(res => {
                    const ownerDetails = res.data; // Owner details
                    this.setState({ ownerDetails });
                })
                .catch(error => {
                    console.log("Error fetching owner details:", error);
                });
    
            
            axiosInstance.get(`/hotels/hotels/${ownerId}`)
                .then(res => {
                    const hotels = res.data; // List of hotels
                    this.setState({ hotels });
                    console.log('Hotelsssss:', this.state.hotels);
                })
                .catch(error => {
                    console.error("Error fetching hotels:", error);
                    console.error(error);
                });

               
        }
       
    }
    

    onHotelClick = (hotelId) => {
        console.log(`onHotelClick called with hotelId: ${hotelId}`);
        const selectedHotel = this.state.hotels.find(hotel => hotel.id === hotelId);
        console.log('Found selectedHotel:', selectedHotel);
        this.setState({ selectedHotel });
        console.log('Hotels:', this.state.hotels);
    };
    
    

    handleAddHotel = (hotelData) => {
        const ownerId = localStorage.getItem("userId");
        axiosInstance.post(`/hotels/create?ownerId=${ownerId}`, hotelData)
            .then(response => {
                console.log("Hotel added successfully:", response.data);
                // actulizez lista de hoteluri după adăugare
                this.setState(prevState => ({
                    hotels: [...prevState.hotels, response.data]
                }));
            })
            .catch(error => {
                console.error("Error adding hotel:", error);
            });
    };

    
    handleUpdateHotel =  (updatedHotel) => {
        try {
            const response = axiosInstance.put('/hotels/update', updatedHotel);
            console.log('Hotel updated successfully:', response.data);
    
            this.setState((prevState) => {
                const updatedHotels = prevState.hotels.map(hotel => {
                    if (hotel.id === updatedHotel.id) {
                        return { ...hotel, ...updatedHotel };
                    }
                    return hotel;
                });
    
                return {
                    hotels: updatedHotels,
                    selectedHotel: updatedHotel // Actualizează hotelul selectat cu datele actualizate
                };
            });
        } catch (error) {
            console.error('Error updating hotel:', error);
        }
    };
    
   
    render() {
        const { ownerDetails, hotels, selectedHotel } = this.state;
        console.log('selectedHotel:', selectedHotel);
        return (
            <React.Fragment>
                {ownerDetails ? (
                    <React.Fragment>
                        <OwnerDetails
                             owner={ownerDetails} 
                             onHotelClick={this.onHotelClick} // Pass the prop to OwnerDetails
                             
                        />

                        <div>
                            <h2>Add Hotel</h2>
                            <AddHotelForm 
                                onAddHotel={this.handleAddHotel} />
                        </div>

                        {selectedHotel && (
                            <div>
                                <h2>Update Hotel</h2>
                                <UpdateHotelForm
                                    hotel={selectedHotel}
                                    onUpdateHotel={this.handleUpdateHotel}
                                    ownerId={ownerDetails.id}
                                />
                            </div>
                        )}
                        

                    </React.Fragment>
                ) : (
                    <p>Loading...</p>
                )}
            </React.Fragment>
        );
    }
    
}

export default OwnerHome;
