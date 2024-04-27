import React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import axiosInstance from "./axios";
import Grid from "@mui/material/Grid";
import history from './history';
import { Link } from 'react-router-dom';
//import { useHistory } from 'react-router-dom'; // Importă hook-ul useHistory

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            loginSuccess: {
                id: 0,
                role: "",
            }
        };
    }

    handleInput = event => {
        const {value, name} = event.target;
        this.setState({
            [name]: value
        });
    };

    onSubmitFunction = event => {
        event.preventDefault();
        let credentials = {
            username: this.state.username,
            password: this.state.password
        }

        /*axiosInstance.post("/login", credentials)
            .then(res => {
                const val = res.data;
                this.setState({ loginSuccess: val }, () => {
                    console.log("Success");
                    console.log(this.state.loginSuccess);

                    if (this.state.loginSuccess.role === "OWNER") {
                        localStorage.setItem("role", this.state.loginSuccess.role);
                        history.push("/owner-home");
                        window.location.reload();
                    }

                    if (this.state.loginSuccess.role === "CLIENT") {
                        localStorage.setItem("role", this.state.loginSuccess.role);
                        history.push("/client-home");
                        window.location.reload();
                    }

                    if (this.state.loginSuccess.role === "ADMIN") {
                        localStorage.setItem("role", this.state.loginSuccess.role);
                        history.push("/admin-home");
                        window.location.reload();
                    }
                });
            })
            .catch(error => {
                console.log(error);
            })*/
        axiosInstance.post("/login", credentials)
            .then(res => {
                const { userId, loginResponse } = res.data; // Extrage ID-ul utilizatorului și răspunsul de la server
                this.setState({ loginSuccess: loginResponse }, () => {
                    console.log("Success");
                    console.log(this.state.loginSuccess);
        
                    // Setarea rolului în funcție de răspunsul primit de la server
                    localStorage.setItem("role", this.state.loginSuccess.role);
                    // Salvarea ID-ului utilizatorului în local storage 
                    localStorage.setItem("userId", userId);
        
                    if (this.state.loginSuccess.role === "OWNER") {
                        history.push("/owner-home");
                    } else if (this.state.loginSuccess.role === "CLIENT") {
                        history.push("/client-home");
                    } else if (this.state.loginSuccess.role === "ADMIN") {
                        history.push("/admin-home");
                    }
        
                    window.location.reload();
                });
            })
            .catch(error => {
                console.log(error);
            });
        
    }


    render() {
        return (
            <Container maxWidth="sm">
                <div>
                    <Grid>
                        <form onSubmit={this.onSubmitFunction}>
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
                                autoComplete="current-password"
                            />

                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                            >
                                Log in
                            </Button>
                            
                          

                        </form>
                    </Grid>
                </div>
            </Container>
        );
    }
}

export default Login;
