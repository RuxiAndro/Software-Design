import React, { Component } from "react";
import axiosInstance from "./axios";
import { List, ListItem, ListItemText,Button } from "@mui/material";


class OwnerHotelsList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      hotels: [],
      loading: true,
      error: null,
      showHotelsList: false 
    };
  }

  componentDidMount() {
    this.fetchHotels();
  }

  componentDidUpdate(prevProps) {
    if (this.props.ownerId !== prevProps.ownerId) {
      this.fetchHotels();
    }
  }

  fetchHotels = async () => {
    const { ownerId } = this.props;
    try {
      console.log("Fetching hotels for ownerId:", ownerId);
      const response = await axiosInstance.get(`/hotels/hotels/${ownerId}`);
      console.log("Response:", response.data);
      this.setState({ hotels: response.data, loading: false, error: null });
    } catch (error) {
      console.error("Error fetching hotels:", error);
      this.setState({ loading: false, error: error.message || "Error fetching hotels" });
    }
  };

  toggleHotelsList = () => {
    this.setState((prevState) => ({
        showHotelsList: !prevState.showHotelsList
    }));
  };


  render() {
    const { hotels, loading, error, showHotelsList } = this.state;
    const { onHotelClick } = this.props; 

    //console.log('onHotelClick:', typeof onHotelClick);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!hotels || hotels.length === 0) {
        return <div>No hotels found for this owner.</div>;
    }

    return (
      <div>
          <Button onClick={this.toggleHotelsList}>
              Lista de hoteluri
          </Button>
          
          { showHotelsList && (
              <div>
                 
                  <ul>
                      {hotels.map((hotel) => (
                          <ListItem
                              key={hotel.id}
                              button
                              onClick={() => onHotelClick(hotel.id)}
                          >
                              <ListItemText primary={hotel.name} />
                          </ListItem>
                      ))}
                  </ul>
                  
              </div>
          )}
      </div>
  );
}

}

export default OwnerHotelsList;
