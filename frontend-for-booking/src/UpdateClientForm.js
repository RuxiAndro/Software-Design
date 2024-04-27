import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import axiosInstance from "./axios";

function UpdateClientForm({ client, onUpdateClient }) {
    const [clientData, setClientData] = useState(client);

    useEffect(() => {
        if (client) {
            setClientData(client);
        }
    }, [client]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setClientData({ ...clientData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axiosInstance.put("/users/update", clientData)
            .then(response => {
                console.log("Client updated successfully:", response.data);
                onUpdateClient(response.data);
            })
            .catch(error => {
                console.error("Error updating client:", error);
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
            {/* Username input */}
            <TextField
                required
                id="username"
                name="username"
                label="Username"
                type="text"
                value={clientData.username}
                onChange={handleChange}
                autoComplete="username"
                variant="outlined"
            />
            {/* Email input */}
            <TextField
                required
                id="email"
                name="email"
                label="Email"
                type="email"
                value={clientData.email}
                onChange={handleChange}
                autoComplete="email"
                variant="outlined"
            />
            {/* Phone Number input */}
            <TextField
                required
                id="phoneNumber"
                name="phoneNumber"
                label="Phone Number"
                type="tel"
                value={clientData.phoneNumber}
                onChange={handleChange}
                autoComplete="tel"
                variant="outlined"
            />
            {/* Role input */}
            <TextField
                required
                id="role"
                name="role"
                label="Role"
                type="text"
                value={clientData.role}
                onChange={handleChange}
                variant="outlined"
            />

            {/* Update button */}
            <Button
                variant="contained"
                color="primary"
                type="submit"
                style={{ margin: '8px' }}
            >
                Update Client
            </Button>
        </Box>
    );
}

export default UpdateClientForm;
