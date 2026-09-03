import { useNavigate } from "react-router";
import { altFromFileName } from "../../utils/altFromFileName";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";
import { userName, logoImagePath } from "../../data/constants";
import PageTitle from "../ui/PageTitle";
import Button from "../ui/Button";

export default function Home() {
    const navigate = useNavigate();

    usePageTitleForBrowserTab("Home");

    return (
        <main id="home">
            <img
                src={logoImagePath}
                alt={altFromFileName(logoImagePath)}
                className="large-page-image"
            />
            <PageTitle title={`Welcome back, ${userName}!`} />
            <h2>Grow your collection, one plant story at a time.</h2>
            <p className="home-page-app-summary">
                Every plant has a little history, from the day you brought it home to the first new
                leaf you noticed. Plant Thyme gives those memories a place to grow.
            </p>
            <div className="home-page-feature-list-container">
                <div className="home-page-feature">
                    <h3>🌿 Keep Tabs on Your Leaves</h3>
                    <p>Track all your leafy friends in one cozy place.</p>
                </div>
                <div className="home-page-feature">
                    <h3>📝 Jot Down the Dirt</h3>
                    <p>Save notes, purchase details, and plant memories.</p>
                </div>
                <div className="home-page-feature">
                    <h3>📸 See How They’ve Grown</h3>
                    <p>Look back on progress photos over time.</p>
                </div>
            </div>
            <Button
                innerText="View My Collection"
                onClick={() => navigate("/currentCollection")}
                className="home-view-collection-btn"
            />
        </main>
    );
}
