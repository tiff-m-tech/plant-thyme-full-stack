import { useState } from "react";
import { useNavigate } from "react-router";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";
import Button from "../ui/Button";
import PageTitle from "../ui/PageTitle";

export default function NotFound() {
    const navigate = useNavigate();
    usePageTitleForBrowserTab("Page Not Found");

    const images = ["missing_404_1.png", "missing_404_2.png", "missing_404_3.png"];

    const [randomImage] = useState(() => {
        const randomIndex = Math.floor(Math.random() * images.length);
        return images[randomIndex];
    });

    return (
        <main id="notFound">
            <PageTitle title="404 - Lost in the Weeds" />
            <img
                src={`${import.meta.env.BASE_URL}images/brand/${randomImage}`}
                alt=""
                className="large-page-image not-found-image"
            />
            <h2>This page wandered off to find more sunlight. 🌿</h2>
            <p>You might have landed here because:</p>
            <ul className="not-found-list">
                <li>✂️ This page may have been pruned.</li>
                <li>🌱 The URL sprouted a typo.</li>
                <li>🌿 Something got tangled in the vines.</li>
                <li>🍂 You followed a link that has gone dormant.</li>
                <li>You're trying to break the site (we see you 👀)</li>
            </ul>
            <p>No worries, let's get you back to your plants 🪴</p>
            <Button
                innerText="Take Me Home"
                onClick={() => navigate("/home")}
                className="not-found-btn"
            />
        </main>
    );
}
