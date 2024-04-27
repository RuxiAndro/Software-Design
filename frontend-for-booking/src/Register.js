import React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import axiosInstance from "./axios";
import Grid from "@mui/material/Grid";
import history from './history';

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            phoneNumber: "" // State property for phone number
        };
    }

    // Handle changes to the input fields
    handleInput = event => {
        const { value, name } = event.target;
        this.setState({
            [name]: value
        });
    };

    // Handle form submission
    onSubmitFunction = event => {
        event.preventDefault();
        // Create user data object and set role to "CLIENT"
        let userData = {
            username: this.state.username,
            password: this.state.password,
            phoneNumber: this.state.phoneNumber,
            role: "CLIENT" // Automatically set role to "CLIENT"
        };
    
        // Post request to create a new user
        axiosInstance.post("/users/create", userData)
            .then(res => {
                console.log("User created successfully");
                // Redirect the user to the login page after successful registration
                history.push("/login");
            })
            .catch(error => {
                console.log("Error occurred during user registration:", error);
            });
    };

    render() {
        return (
            <Container maxWidth="sm">
                <div>
                    <Grid>
                        {/* Registration form */}
                        <form onSubmit={this.onSubmitFunction}>
                            {/* Username input */}
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                id="username"
                                label="Username"
                                name="username"
                                autoComplete="string"
                                onChange={this.handleInput}
                                autoFocus
                            />
                            {/* Password input */}
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                onChange={this.handleInput}
                                autoComplete="new-password"
                            />
                            {/* Phone Number input */}
                            <TextField
                                variant="outlined"
                                margin="normal"
                                fullWidth
                                name="phoneNumber"
                                label="Phone Number"
                                id="phoneNumber"
                                onChange={this.handleInput}
                            />
                            {/* Register button */}
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                            >
                                Register
                            </Button>
                        </form>
                    </Grid>
                </div>
            </Container>
        );
    }
}

export default Register;
