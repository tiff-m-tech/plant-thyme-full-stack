import { useState } from "react";
import { Link, useNavigate } from "react-router";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";
import { altFromFileName } from "../../utils/altFromFileName";
import { logoImagePath } from "../../constants";
import { loginUser, saveToken } from "../../services/api"; // NEW
import PageTitle from "../ui/PageTitle";
import Button from "../ui/Button";

export default function LogIn({ setIsLoggedIn }) {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    // Sends the username + password to the backend. If correct, saves the token and logs in.
    async function handleSubmit(event) {
        event.preventDefault();
        setError("");

        try {
            const data = await loginUser(username, password);
            saveToken(data.token);
            setIsLoggedIn(true);
            navigate("/home");
        } catch (err) {
            // Backend errors have a statusCode + message, e.g. "Invalid username or password."
            // Anything else (like the server being off) gets a general message.
            setError(err.statusCode ? err.message : "Could not log in. Please try again.");
        }
    }

    usePageTitleForBrowserTab("Log In");

    return (
        <main id="logIn">
            <img
                src={logoImagePath}
                alt={altFromFileName(logoImagePath)}
                className="large-page-image"
            />
            <PageTitle title="Log In" />
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username <span className="red-font">*</span>
                </label>
                <input
                    id="username"
                    type="text"
                    name="username"
                    placeholder="♙ Type Your username"
                    autoComplete="username"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                    required
                />
                <label htmlFor="password">
                    Password <span className="red-font">*</span>
                </label>
                <input
                    id="password"
                    type="password"
                    name="password"
                    placeholder="🔒︎ Type Your Password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    required
                />
                {error && <p className="red-font">{error}</p>}
                <Button innerText="Login" type="submit" />
            </form>
            <p className="auth-link">
                Don't have an account? <Link to="/register">Sign up</Link>
            </p>
            <div className="mock-credentials">
                <strong>Demo login</strong>
                <p>Username: tiffany</p>
                <p>Password: plantthyme</p>
            </div>
        </main>
    );
}
