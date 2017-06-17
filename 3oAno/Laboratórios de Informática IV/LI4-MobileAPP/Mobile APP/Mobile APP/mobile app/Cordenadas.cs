using System;

namespace Mobile_APP
{
    public class Cordenadas
    {
        private double latitude;
        private double longitude;

        public Cordenadas() {

            this.latitude = 0.0f;
            this.longitude = 0.0f;
        }

        public Cordenadas(double lgtd, double lat) {

            this.longitude = lgtd;
            this.latitude = lat;

        }

        public String toString() {

            return "Latitude: " + latitude + ", Longitude: " + longitude;
        }

        public double Latidude {

        get { return latitude; }

        }

        public double Longitude
        {

            get { return longitude; }

        }


    }
}