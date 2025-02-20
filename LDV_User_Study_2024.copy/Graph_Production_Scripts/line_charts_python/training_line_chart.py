import matplotlib.pyplot as plt

"""
###################################################
DEFINE YOUR PARAMETERS HERE
###################################################
"""
# Define your output file name and extension
file_name = "training_line_chart"  # Do not include the file extension
file_extension = ".png"  # File extension

# Define your destination device
device = "tablet" #set this to "tablet" or "phone"

# Define your line thickness for the different series
line_thickness_mm = 9  # Desired line thickness in mm

# Graph parameters
x_axis_label = "Year"
y_axis_label = "Millions (Individuals)"
title = "Animal Population over Time"
x_axis_min = 1900
x_axis_max = 2000
y_axis_min = 0
y_axis_max = 100
grid_color = 'black'

line_x_series1 = [x_axis_min + x * (x_axis_max - x_axis_min) for x in [0, 0.5, 1.0]] # X coordinates of the line points for Series 1, scaled accordingly. Change percentages as needed
line_y_series1 = [y_axis_min + y * (y_axis_max - y_axis_min) for y in [0.0, 0.5, 0.1]] # Y coordinates of the line points for Series 1

# line_x_series2 = [x_axis_min + x * (x_axis_max - x_axis_min) for x in [0, 0.33, 0.66, 1.0]] # X coordinates of the line points for Series 2
# line_y_series2 = [y_axis_min + y * (y_axis_max - y_axis_min) for y in [0, 0.33, 0.66, 0.7]] # Y coordinates of the line points for Series 2

line_x_series3 = [x_axis_min + x * (x_axis_max - x_axis_min) for x in [0.15, 0.5, 1.0]] # X coordinates of the line points for Series 3
line_y_series3 = [y_axis_min + y * (y_axis_max - y_axis_min) for y in [1.0,  0.5, 1.0]] # Y coordinates of the line points for Series 3

# Define your parameters for the intersection points
point_size_mm = 13  # Desired intersection point size in mm
points_x = [x_axis_min + x * (x_axis_max - x_axis_min) for x in [0.5]] # X coordinate for the intersection points
points_y = [y_axis_min + y * (y_axis_max - y_axis_min) for y in [0.5]] # Y coordinate for the intersection points
points_color = (0/255, 0/255, 255/255) # Blue

# Define the device's dpi (OPTIONAL, but recommended if you know the correct value)
tablet_dpi = 340  # standard tablet DPI 

# Define the line colors in RGB and the labels for each series
color1 = (0/255, 0/255, 254/255) # Blue for series 1
label1 = "Animal 1"
color2 = (0/255, 0/255, 253/255) # Blue for series 2
label2 = 'Animal 2'
color3 = (0/255, 0/255, 252/255) # Blue for series 3
label3 = 'Animal 3'

"""
###################################################
AUTOMATED FROM THIS POINT ONWARD
###################################################
"""
if device == "tablet" :
     plt.figure(figsize=(10,6.5)) # Estimate in inches for landscape tablet screen dimensions
elif device == "phone" :
     # Start figure object and define size
    plt.figure(figsize=(6,3)) # Estimate in inches for landscape phone screen dimensions
else :
     print ("Please define device type as 'tablet' or 'phone'.")

# Calculate intersection point size in typographic point units
line_thickness_in = line_thickness_mm / 25.4  # Convert mm to inches
line_thickness_typo = line_thickness_in * 72  # Convert inches to typographic points
line_thickness = line_thickness_typo  # Final line thickness in typographic points (square of typo points)

# Calculate intersection point size in typographic point units
point_size_in = point_size_mm / 25.4  # Convert mm to inches
point_size_typo = point_size_in * 72  # Convert inches to typographic points
point_size = point_size_typo ** 2  # Final point size in typographic points (square of typo points)

# Plot the line chart(s)
plt.plot(
    line_x_series1, line_y_series1, 
    marker='o', 
    linestyle='-', 
    color=color1,
    linewidth=line_thickness,
    label=label1
)
# plt.plot(
#     line_x_series2, line_y_series2, 
#     marker='o', 
#     linestyle='-', 
#     color=color2,
#     linewidth=line_thickness,
#     label=label2
# )
plt.plot(
    line_x_series3, line_y_series3, 
    marker='o', 
    linestyle='-', 
    color=color3,
    linewidth=line_thickness,
    label=label3
)

# Plot each point
for i in range(len(points_x)):
    plt.scatter(points_x[i], points_y[i],
                color=points_color, edgecolor=points_color,
                s=point_size, zorder=2)

# Add labels and title
plt.xlabel(x_axis_label)
plt.ylabel(y_axis_label)
plt.title(title)

# Setting axis limits manually to ensure 0 and 1 are at the edges
plt.xlim(x_axis_min, x_axis_max)
plt.ylim(y_axis_min, y_axis_max)

# Add grid and save the plot
plt.grid(True, color=grid_color)
plt.savefig(file_name + file_extension, transparent=False, bbox_inches='tight', dpi=tablet_dpi) 
plt.show()