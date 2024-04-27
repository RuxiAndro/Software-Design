import React, { useState } from 'react';
import axiosInstance from './axios';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';

function AddClientForm({ onAddClient }) {
    // Initialize the client data state with fields for email, role, username, and phone number
    const [clientData, setClientData] = useState({
        email: '',
        role: '',
        username: '',
        phoneNumber: '',
    });

    // Handle changes to the form inputs
    const handleChange = (e) => {
        const { name, value } = e.target;
        setClientData({ ...clientData, [name]: value });
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        axiosInstance.post('/users/create', clientData)
            .then((response) => {
                console.log('Client added successfully:', response.data);
                onAddClient(response.data);
                // Reset the form fields after successful submission
                setClientData({
                    email: '',
                    role: '',
                    username: '',
                    phoneNumber: '',
                });
            })
            .catch((error) => {
                console.error('Error adding client:', error);
            });
    };

    return (
        <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{
                '& .MuiTextField-root': { m: 1, width: '25ch' },
            }}
            noValidate
            autoComplete="off"
        >
            {/* Email input */}
            <TextField
                type="email"
                name="email"
                value={clientData.email}
                onChange={handleChange}
                label="Email"
                variant="outlined"
                required
            />
            {/* Role input */}
            <TextField
                type="text"
                name="role"
                value={clientData.role}
                onChange={handleChange}
                label="Role"
                variant="outlined"
                required
            />
            {/* Username input */}
            <TextField
                type="text"
                name="username"
                value={clientData.username}
                onChange={handleChange}
                label="Username"
                variant="outlined"
                required
            />
            {/* Phone Number input */}
            <TextField
                type="text"
                name="phoneNumber"
                value={clientData.phoneNumber}
                onChange={handleChange}
                label="Phone Number"
                variant="outlined"
                required
            />
            {/* Add Client button */}
            <Button
                variant="contained"
                color="primary"
                type="submit"
              //  startIcon={<AddIcon />}
                style={{ margin: '8px' }}
            >
                Add User
            </Button>
        </Box>
    );
}

export default AddClientForm;
