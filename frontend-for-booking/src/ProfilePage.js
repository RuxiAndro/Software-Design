import React from "react";
import UserDetails from "./UserDetails"; // presupunând că acest fișier este în același director

class ProfilePage extends React.Component {
    render() {
        const user = {
            id: 1,
            username: "john_doe",
            password: "********"
        };

        return (
            <div>
                <h1>Profilul utilizatorului</h1>
                <UserDetails user={user} />
            </div>
        );
    }
}

export default ProfilePage;