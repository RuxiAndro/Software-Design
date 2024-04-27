import React, { useState } from "react";

function UpdateHotelForm({ hotel, onUpdateHotel, ownerId  }) {
    const [formData, setFormData] = useState({
        id: hotel.id,
        name: hotel.name,
        description: hotel.description,
        location: hotel.location,
        numberOfRooms: hotel.numberOfRooms,
        ownerId: ownerId, // Consider if this field should be read-only
    });

   
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    
    const handleSubmit = (e) => {
        e.preventDefault();
        onUpdateHotel(formData);
    };

    return (
        <form onSubmit={handleSubmit}>
            {/* Hotel ID input */}
            <div>
                <label htmlFor="id">Hotel ID:</label>
                <input
                    type="text"
                    name="id"
                    id="id"
                    value={formData.id}
                    readOnly // Make the hotel ID field read-only
                />
            </div>
            
            {/* Name input */}
            <div>
                <label htmlFor="name">Name:</label>
                <input
                    type="text"
                    name="name"
                    id="name"
                    value={formData.name}
                    onChange={handleChange}
                />
            </div>

            {/* Description input */}
            <div>
                <label htmlFor="description">Description:</label>
                <textarea
                    name="description"
                    id="description"
                    value={formData.description}
                    onChange={handleChange}
                />
            </div>

            {/* Location input */}
            <div>
                <label htmlFor="location">Location:</label>
                <input
                    type="text"
                    name="location"
                    id="location"
                    value={formData.location}
                    onChange={handleChange}
                />
            </div>

            {/* Number of rooms input */}
            <div>
                <label htmlFor="numberOfRooms">Number of Rooms:</label>
                <input
                    type="number"
                    name="numberOfRooms"
                    id="numberOfRooms"
                    value={formData.numberOfRooms}
                    onChange={handleChange}
                />
            </div>

           {/* Owner ID input */}
           <div>
                <label htmlFor="ownerId">Owner ID:</label>
                <input
                    type="text"
                    name="ownerId"
                    id="ownerId"
                    value={formData.ownerId}
                    readOnly // Make the ownerId field read-only
                />
            </div>

            {/* Submit button */}
            <button type="submit">Update Hotel</button>
        </form>
    );
}

export default UpdateHotelForm;
