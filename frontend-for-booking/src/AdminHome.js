import React, { useState, useEffect } from "react";
import axiosInstance from "./axios";
import { List, ListItem, ListItemText, Button, Stack } from "@mui/material";
import AddClientForm from "./AddClientForm";
import UpdateClientForm from "./UpdateClientForm";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import AddIcon from "@mui/icons-material/Add";

function AdminHome() {
    const [clients, setClients] = useState([]);
    const [selectedClient, setSelectedClient] = useState(null);
    const [isAddingClient, setIsAddingClient] = useState(false);

    // Fetch all clients when the component mounts
    useEffect(() => {
        axiosInstance.get("/users/Users")
            .then(res => {
                setClients(res.data);
            })
            .catch(error => {
                console.error("Error fetching clients:", error);
            });
    }, []);

    // Function to add a new client
    const handleAddClient = (clientData) => {
        axiosInstance.post("/users/create", clientData)
            .then(res => {
                console.log("User added successfully:", res.data);
                alert("User added successfully");
                setClients(prevClients => [...prevClients, res.data]);
                // Reset the form and close the Add User form
                setIsAddingClient(false);
            })
            .catch(error => {
                console.error("Error adding user:", error);
                alert("Error adding user");
            });
    };

    // Function to update a client
    const handleUpdateClient = (updatedClient) => {
        axiosInstance.put("/users/update", updatedClient)
            .then(res => {
                console.log("User updated successfully:", res.data);
                alert("User updated successfully");
                setClients(prevClients => prevClients.map(client =>
                    client.id === updatedClient.id ? res.data : client
                ));
                // Reset the selected client
                setSelectedClient(null);
            })
            .catch(error => {
                console.error("Error updating user:", error);
                alert("Error updating user");
            });
    };

    // Function to delete a client
    const handleDeleteClient = (clientId) => {
        const confirmDelete = window.confirm("Are you sure you want to delete this user?");
        if (confirmDelete) {
            axiosInstance.delete(`/users/delete/${clientId}`)
                .then(() => {
                    console.log("User deleted successfully");
                    alert("User deleted successfully");
                    setClients(prevClients => prevClients.filter(client => client.id !== clientId));
                })
                .catch(error => {
                    console.error("Error deleting user:", error);
                    alert("Error deleting user");
                });
        }
    };

    // Function to handle selecting a client for update
    const selectClient = (client) => {
        setSelectedClient(client);
        setIsAddingClient(false);
    };

    // Function to handle adding a new client
    const handleAddClientClick = () => {
        setIsAddingClient(true);
        setSelectedClient(null);
    };

    return (
        <div>
            {/* List of clients */}
            <List>
                {clients.map(client => (
                    <ListItem key={client.id} button onClick={() => selectClient(client)}>
                        <ListItemText primary={client.username} />
                        <Stack direction="row" spacing={1}>
                            <Button
                                variant="outlined"
                                color="error"
                                startIcon={<DeleteIcon />}
                                onClick={() => handleDeleteClient(client.id)}
                            >
                                Delete
                            </Button>
                            <Button
                                variant="contained"
                                color="primary"
                                startIcon={<EditIcon />}
                                onClick={() => selectClient(client)}
                            >
                                Update
                            </Button>
                        </Stack>
                    </ListItem>
                ))}
            </List>

            {/* Button to open Add User form */}
            <Stack direction="row" spacing={1} mb={2}>
                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<AddIcon />}
                    onClick={handleAddClientClick}
                >
                    Add User
                </Button>
            </Stack>

            {/* Show Add User form or Update User form based on state */}
            {isAddingClient && (
                <AddClientForm onAddClient={handleAddClient} />
            )}

            {selectedClient && (
                <UpdateClientForm
                    client={selectedClient}
                    onUpdateClient={handleUpdateClient}
                />
            )}
        </div>
    );
}

export default AdminHome;
